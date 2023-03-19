package com.example.education.data.network.repository

import android.os.SystemClock
import com.example.education.data.network.OpenWeatherApiService
import com.example.education.data.network.model.mapToWeatherEntity
import com.example.education.domain.model.WeatherEntity
import com.example.education.domain.repository.WeatherRepositoryByCityName
import com.example.education.domain.repository.WeatherRepositoryByCoordinate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryRemote(
    private val remoteSource: OpenWeatherApiService,
) : WeatherRepositoryByCoordinate, WeatherRepositoryByCityName {

    override suspend fun getWeatherInfo(city: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            remoteSource.getWeatherByCityName(city = city)
                ?.mapToWeatherEntity()?.apply { timeRequest = SystemClock.uptimeMillis() }
        }
    }

    override suspend fun getWeatherInfoByCoordinate(lat: Double, lon: Double): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            remoteSource.getWeatherByCoordinate(lat = lat, lon = lon)?.mapToWeatherEntity()
        }
    }
}
