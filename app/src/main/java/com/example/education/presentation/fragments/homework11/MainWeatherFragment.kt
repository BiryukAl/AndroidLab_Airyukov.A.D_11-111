package com.example.education.presentation.fragments.homework11

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.education.R
import com.example.education.data.model.WeatherResponse
import com.example.education.data.repository.WeatherRepository
import com.example.education.databinding.FragmentMainWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch


class MainWeatherFragment : Fragment(R.layout.fragment_main_weather) {

    private val viewBinding: FragmentMainWeatherBinding
            by viewBinding(FragmentMainWeatherBinding::bind)

    private lateinit var pLauncher: ActivityResultLauncher<String>
    private var fLocationClient: FusedLocationProviderClient? = null
    private var glide: RequestManager? = null

    private val repositoryWhether = WeatherRepository()

    private var weatherResponse: WeatherResponse? = null
        set(value) {
            val urlIcon =
                "http://openweathermap.org/img/wn/${value?.weather?.first()?.icon ?: "10d"}@2x.png"
            glide?.load(urlIcon)?.preload()
            field = value
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glide = Glide.with(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        visibilityTempMain(false)
        showProgressBar(false)

        with(viewBinding) {
            btnSearchByCity.setOnClickListener {
                getWeatherForCity(etCitySearch.text.toString())
            }

            btnSearchByGps.setOnClickListener {
                getWeatherForLocation()
            }

            mainTemp.setOnClickListener {
                weatherResponse?.let { it1 -> showDetailedWeather(it1) }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fLocationClient = null
    }

    private fun getWeatherForCity(city: String) {
        lifecycleScope.launch {
            runCatching {
                weatherResponse = repositoryWhether.getWeatherByCityName(city)
            }.onSuccess {
                showProgressBar(false)
                visibilityTempMain(true)
            }.onFailure {
                showProgressBar(false)
                Toast.makeText(activity, "Error weather", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeatherForLocation() {
        checkPermission()
        if (!isLocationEnabled()) {
            locationSettingDialog(requireContext(), object : ListenerDialogGps {
                override fun onClick() {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
            return
        }
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val token = CancellationTokenSource().token
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
            return
        }
        visibilityTempMain(false)
        showProgressBar(true)
        fLocationClient?.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token)
            ?.addOnCompleteListener {
                val lat = it.result.latitude
                val lon = it.result.longitude
                lifecycleScope.launch {
                    runCatching {
                        weatherResponse = repositoryWhether.getWeatherByCoordinate(lat, lon)
                    }.onSuccess {
                        showProgressBar(false)
                        visibilityTempMain(true)
                    }.onFailure {
                        showProgressBar(false)
                        Toast.makeText(activity, "Error weather", Toast.LENGTH_SHORT).show()
                    }
                }
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

    private fun showProgressBar(on: Boolean) {
        viewBinding.progressBarWeather.visibility = if (on) View.VISIBLE else View.GONE
    }

    private fun visibilityTempMain(on: Boolean) {
        if (on && weatherResponse != null) {
            viewBinding.mainTemp.text = (weatherResponse!!.main?.temp ?: 0.0).toString()
            viewBinding.nameCityMain.text = weatherResponse!!.cityName ?: "NaN"
        }

        viewBinding.mainTemp.visibility = if (on) View.VISIBLE else View.INVISIBLE
        viewBinding.nameCityMain.visibility = if (on) View.VISIBLE else View.INVISIBLE
    }


    @SuppressLint("SetTextI18n")
    fun showDetailedWeather(weatherResponse: WeatherResponse) {
        val bottomSheet = BottomSheetDialog(requireContext())

        bottomSheet.setContentView(R.layout.detailed_weather_for_bottom_sheet)

        bottomSheet.findViewById<TextView>(R.id.cityName)?.text =
            "${resources.getString(R.string.nameCity)} ${weatherResponse.cityName}"
        bottomSheet.findViewById<TextView>(R.id.temp)?.text = "${weatherResponse.main?.temp}"
        bottomSheet.findViewById<TextView>(R.id.humidity)?.text =
            "${resources.getString(R.string.humidity)} ${weatherResponse.main?.humidity}"
        bottomSheet.findViewById<TextView>(R.id.pressure)?.text =
            "${resources.getString(R.string.pressure)} ${weatherResponse.main?.pressure}"
        bottomSheet.findViewById<TextView>(R.id.wind_speed)?.text =
            "${resources.getString(R.string.wind_speed)} ${weatherResponse.wind?.speed}"

        val icon = bottomSheet.findViewById<ImageView>(R.id.weatherIcon)
        val urlIcon =
            "http://openweathermap.org/img/wn/${
                weatherResponse
                    .weather?.first()?.icon ?: "10d"
            }@2x.png"

        glide?.load(urlIcon)?.into(icon!!)
        bottomSheet.show()
    }

    companion object {
        const val MAIN_WEATHER_FRAGMENT_TAG = "MAIN_WEATHER_FRAGMENT_TAG"

        fun getInstance() = MainWeatherFragment()

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
