package com.example.education.domain.usecase

import com.example.education.domain.model.WeatherEntity
import com.example.education.domain.repository.WeatherRepositoryByCoordinate

class WeatherByCoordinateUseCase(
    private val remoteRepository: WeatherRepositoryByCoordinate,
) {
    suspend operator fun invoke(lat: Double, lon: Double): WeatherEntity? {
        return remoteRepository.getWeatherInfoByCoordinate(lat, lon)
    }
}
