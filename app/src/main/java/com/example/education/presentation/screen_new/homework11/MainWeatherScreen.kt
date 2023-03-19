package com.example.education.presentation.screen_new.homework11

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.MutableCreationExtras
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.education.R
import com.example.education.databinding.FragmentMainWeatherBinding
import com.example.education.di.ServiceLocator
import com.example.education.di.ViewModelArgsKeys
import com.example.education.domain.model.WeatherEntity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource


class MainWeatherScreen : Fragment(R.layout.fragment_main_weather) {
    private val viewBinding: FragmentMainWeatherBinding
            by viewBinding(FragmentMainWeatherBinding::bind)

    private val viewModel: MainWeatherViewModel by viewModels(
        extrasProducer = {
            MutableCreationExtras().apply {
                set(ViewModelArgsKeys.weatherGetByCityInteractor,
                    ServiceLocator.weatherGetByCityInteractor)
                set(ViewModelArgsKeys.weatherByCoordinateUseCase,
                    ServiceLocator.weatherByCoordinateUseCase)
            }
        }
    ) {
        MainWeatherViewModel.factory
    }

    private lateinit var pLauncher: ActivityResultLauncher<String>
    private var fLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onDestroy() {
        fLocationClient = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        observeViewState()

        with(viewBinding) {
            btnSearchByCity.setOnClickListener {
                viewModel.getWeatherByCity(etCitySearch.text.toString())
            }

            btnSearchByGps.setOnClickListener {
                getWeatherForLocation()
            }
        }
    }


    private fun observeViewState() {
        viewModel.state.observe(viewLifecycleOwner
        ) { state ->
            when (state) {
                is WeatherState.DefaultState -> {
                    visibleProgressBar(false)
                    renderMainInfo(false, null)
                }
                is WeatherState.ErrorState -> {
                    visibleProgressBar(false)
                    Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
                }
                is WeatherState.LoadedState -> {
                    visibleProgressBar(false)
                    renderMainInfo(true, state.data)
                    viewBinding.mainTemp.setOnClickListener {
                        showDetailedWeather(state.data)
                    }
                }
                is WeatherState.LoadingState -> {
                    renderMainInfo(false, null)
                    visibleProgressBar(true)
                }
            }
        }
    }

    private fun showDetailedWeather(weatherEntity: WeatherEntity) {
        val dialog = DetailedWeatherDialog.getInstance(weatherEntity)

        dialog.show(childFragmentManager,
            DetailedWeatherDialog.DETAILED_WEATHER_DIALOG_TAG)
    }

    private fun renderMainInfo(visible: Boolean, weatherEntity: WeatherEntity?) {
        if (visible && weatherEntity != null) {
            viewBinding.nameCityMain.text =
                getString(R.string.nameCity_pattern, weatherEntity.cityName)
            viewBinding.mainTemp.text =
                getString(R.string.temp_pattern, weatherEntity.temp.toString())
        }
        viewBinding.mainTemp.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        viewBinding.nameCityMain.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    private fun visibleProgressBar(visible: Boolean) {
        viewBinding.progressBarWeather.visibility = if (visible) View.VISIBLE else View.GONE
    }


    // GPS
    private fun getWeatherForLocation() {
        checkPermission()
        if (!isLocationEnabled()) {
            locationSettingDialog(requireContext(),
                object : ListenerDialogGps {
                    override fun onClick() {
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                })
            return
        }
        val token = CancellationTokenSource().token
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
            return
        }
        fLocationClient?.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token)
            ?.addOnCompleteListener {
                val lat = it.result.latitude
                val lon = it.result.longitude
                viewModel.getWeatherByCoordinate(lat, lon)
            }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {
            viewBinding.btnSearchByGps.isEnabled = it
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(activity as AppCompatActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    companion object {
        const val MAIN_WEATHER_SCREEN_TAG = "MAIN_WEATHER_SCREEN_TAG"

        fun getInstance() = MainWeatherScreen()

        fun locationSettingDialog(context: Context, listenerDialogGps: ListenerDialogGps) {
            val builder = AlertDialog.Builder(context)
            val dialog = builder.create()
            dialog.setTitle("Enable Location?")
            dialog.setMessage("Location disable, do you want enable location?")
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
                listenerDialogGps.onClick()
                dialog.dismiss()
            }
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { _, _ -> dialog.dismiss() }
            dialog.show()
        }

        interface ListenerDialogGps {
            fun onClick()
        }
    }
}
