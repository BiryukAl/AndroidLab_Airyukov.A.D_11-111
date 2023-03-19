package com.example.education.domain.usecase

import com.example.education.domain.model.WeatherEntity
import com.example.education.domain.repository.WeatherRepositoryByCityName

class WeatherByCityUseCase(
    private val localRepository: WeatherRepositoryByCityName,
    private val remoteRepository: WeatherRepositoryByCityName,
) {

    suspend fun findForLocal(cityName: String): WeatherEntity? {
        return localRepository.getWeatherInfo(cityName)
    }

    suspend fun findForRemote(cityName: String): WeatherEntity? {
        return remoteRepository.getWeatherInfo(cityName)
    }
}
