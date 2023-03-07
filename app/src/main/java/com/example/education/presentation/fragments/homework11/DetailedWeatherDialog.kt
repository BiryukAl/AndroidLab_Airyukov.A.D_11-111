package com.example.education.presentation.fragments.homework11

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.education.R
import com.example.education.data.model.WeatherResponse
import com.example.education.databinding.DetailedWeatherForBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailedWeatherDialog :
    BottomSheetDialogFragment(R.layout.detailed_weather_for_bottom_sheet) {

    private val viewBinding: DetailedWeatherForBottomSheetBinding
            by viewBinding(DetailedWeatherForBottomSheetBinding::bind)

    private var glide: RequestManager? = null

    private val glidePhotoPrefix: String = "https://openweathermap.org/img/wn/"
    private val glidePhotoSuffix: String = "@2x.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glide = Glide.with(this)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            cityName.text = "${resources.getString(R.string.nameCity)} ${
                arguments?.getString(
                    WEATHER_CITY_NAME_ARG)
            }"
            temp.text = "${arguments?.getDouble(WEATHER_MAIN_TEMP_ARG)}"
            humidity.text = "${resources.getString(R.string.humidity)} ${
                arguments?.getInt(
                    WEATHER_MAIN_HUMIDITY_ARG)
            }"
            pressure.text = "${resources.getString(R.string.pressure)} ${
                arguments?.getInt(
                    WEATHER_MAIN_PRESSURE_ARG)
            }"
            windSpeed.text = "${resources.getString(R.string.wind_speed)} ${
                arguments?.getDouble(
                    WEATHER_WIND_SPEED_ARG)
            }"

            val urlIcon =
                "$glidePhotoPrefix${arguments?.getString(WEATHER_ICON_ARG) ?: "10d"}$glidePhotoSuffix"
            glide?.load(urlIcon)?.into(weatherIcon)
        }
    }


    companion object {
        const val DETAILED_WEATHER_DIALOG_TAG = "DETAILED_WEATHER_DIALOG_TAG"

        const val WEATHER_CITY_NAME_ARG = "WEATHER_CITY_NAME_ARG"
        const val WEATHER_MAIN_TEMP_ARG = "WEATHER_MAIN_TEMP_ARG"
        const val WEATHER_MAIN_HUMIDITY_ARG = "WEATHER_MAIN_HUMIDITY_ARG"
        const val WEATHER_MAIN_PRESSURE_ARG = "WEATHER_MAIN_PRESSURE_ARG"
        const val WEATHER_WIND_SPEED_ARG = "WEATHER_WIND_SPEED_ARG"
        const val WEATHER_ICON_ARG = "WEATHER_ICON_ARG"

        fun getInstance(weatherResponse: WeatherResponse): DialogFragment {
            val fragment = DetailedWeatherDialog()

            val args = Bundle().apply {
                putString(WEATHER_CITY_NAME_ARG, weatherResponse.cityName)
                putDouble(WEATHER_MAIN_TEMP_ARG, weatherResponse.main?.temp ?: 0.0)
                putInt(WEATHER_MAIN_HUMIDITY_ARG, weatherResponse.main?.humidity ?: 0)
                putInt(WEATHER_MAIN_PRESSURE_ARG, weatherResponse.main?.pressure ?: 0)
                putDouble(WEATHER_WIND_SPEED_ARG, weatherResponse.wind?.speed ?: 0.0)
                putString(WEATHER_ICON_ARG, weatherResponse.weather?.first()?.icon ?: "10d")

            }
            fragment.arguments = args
            return fragment
        }
    }
}