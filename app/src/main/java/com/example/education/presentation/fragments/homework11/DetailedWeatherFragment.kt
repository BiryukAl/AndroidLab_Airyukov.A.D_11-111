package com.example.education.presentation.fragments.homework11

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.education.R
import com.example.education.data.model.WeatherResponse
import com.example.education.databinding.FragmentDetailedWeatherBinding

class DetailedWeatherFragment : Fragment(R.layout.fragment_detailed_weather) {

    private val viewBinding: FragmentDetailedWeatherBinding
            by viewBinding(FragmentDetailedWeatherBinding::bind)

    private var glide: RequestManager? = null

    private val glidePhotoPrefix:String = "http://openweathermap.org/img/wn/"
    private val glidePhotoSuffix:String = "@2x.png"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glide = Glide.with(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    companion object {
        const val DETAILED_WEATHER_FRAGMENT_TAG = "DETAILED_WEATHER_FRAGMENT_TAG"

        const val WEATHER_CITY_NAME_ARG = "WEATHER_CITY_NAME_ARG"
        const val WEATHER_MAIN_TEMP_ARG = "WEATHER_MAIN_TEMP_ARG"
        const val WEATHER_MAIN_HUMIDITY_ARG = "WEATHER_MAIN_HUMIDITY_ARG"
        const val WEATHER_MAIN_PRESSURE_ARG = "WEATHER_MAIN_PRESSURE_ARG"
        const val WEATHER_WIND_SPEED_ARG = "WEATHER_WIND_SPEED_ARG"
        const val WEATHER_ICON_ARG = "WEATHER_ICON_ARG"


        fun getInstance(weatherResponse: WeatherResponse): DetailedWeatherFragment {

            val args = Bundle().apply {
                putString(WEATHER_CITY_NAME_ARG, weatherResponse.cityName)
                putDouble(WEATHER_MAIN_TEMP_ARG, weatherResponse.main?.temp?:0.0)
                putInt(WEATHER_MAIN_HUMIDITY_ARG, weatherResponse.main?.humidity?:0)
                putInt(WEATHER_MAIN_PRESSURE_ARG, weatherResponse.main?.pressure?:0)
                putDouble(WEATHER_WIND_SPEED_ARG, weatherResponse.wind?.speed?:0.0)
                putString(WEATHER_ICON_ARG, weatherResponse.weather?.first()?.icon?:"10d")
            }
            val fragment = DetailedWeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }
}