package com.example.education.data.repository

import com.example.education.data.model.WeatherResponse
import com.example.education.data.network.OpenWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    suspend fun getWeatherByCityName(city: String): WeatherResponse? {
        Result
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance()?.getWeatherByCityName(city = city)
        }
    }

    suspend fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherResponse? {
        Result
        return withContext(Dispatchers.IO) {
            OpenWeatherService.getInstance()?.getWeatherByCoord(lat = lat, lon = lon)
        }
    }
}
