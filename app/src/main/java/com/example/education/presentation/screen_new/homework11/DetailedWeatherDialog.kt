package com.example.education.presentation.screen_new.homework11

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.education.R
import com.example.education.databinding.DetailedWeatherForBottomSheetBinding
import com.example.education.domain.model.WeatherEntity
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            cityName.text = getString(R.string.nameCity_pattern, arguments?.getString(
                WEATHER_CITY_NAME_ARG))
            temp.text = getString(R.string.temp_pattern,
                arguments?.getDouble(WEATHER_MAIN_TEMP_ARG).toString())

            humidity.text = getString(R.string.humidity_pattern, arguments?.getInt(
                WEATHER_MAIN_HUMIDITY_ARG).toString())
            pressure.text = getString(R.string.pressure_pattern, arguments?.getInt(
                WEATHER_MAIN_PRESSURE_ARG).toString())

            windSpeed.text = getString(R.string.wind_speed_pattern, arguments?.getDouble(
                WEATHER_WIND_SPEED_ARG).toString())
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

        fun getInstance(weatherEntity: WeatherEntity): DialogFragment {
            val fragment = DetailedWeatherDialog()

            val args = Bundle().apply {
                putString(WEATHER_CITY_NAME_ARG, weatherEntity.cityName)
                putDouble(WEATHER_MAIN_TEMP_ARG, weatherEntity.temp)
                putInt(WEATHER_MAIN_HUMIDITY_ARG, weatherEntity.humidity)
                putInt(WEATHER_MAIN_PRESSURE_ARG, weatherEntity.pressure)
                putDouble(WEATHER_WIND_SPEED_ARG, weatherEntity.windSpeed)
                putString(WEATHER_ICON_ARG, weatherEntity.icon)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
