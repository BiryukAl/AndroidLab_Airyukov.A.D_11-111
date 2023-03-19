package com.example.education.domain.interactor

import com.example.education.domain.model.WeatherEntity
import com.example.education.domain.usecase.WeatherByCityUseCase
import com.example.education.domain.usecase.WeatherSaveUseCase

class WeatherGetByCityInteractor(
    private val weatherByCityUseCase: WeatherByCityUseCase,
    private val weatherSaveUseCase: WeatherSaveUseCase,
) {

    suspend operator fun invoke(cityName: String, timeRequest: Long): WeatherEntity? {
        val weatherCache = weatherByCityUseCase.findForLocal(cityName)

        return if (weatherCache != null && weatherCache.timeRequest + WEATHER_TIMEOUT_CACHE > timeRequest) {
            // DB
            weatherCache
        } else {
            // API
            val newWeatherCache = weatherByCityUseCase.findForRemote(cityName)
            newWeatherCache?.let {
                it.cityName = cityName
                it.timeRequest = timeRequest
                weatherSaveUseCase(it)
            }
            newWeatherCache
        }
    }

    companion object {
        const val WEATHER_TIMEOUT_CACHE = 60000L // 1min == 60000 milliseconds
    }
}
