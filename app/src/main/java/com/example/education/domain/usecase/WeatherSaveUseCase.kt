package com.example.education.domain.usecase

import com.example.education.domain.model.WeatherEntity
import com.example.education.domain.repository.WeatherSaveRepository

class WeatherSaveUseCase(
    private val localRepository: WeatherSaveRepository,
) {
    suspend operator fun invoke(weatherEntity: WeatherEntity) {
        localRepository.addWeatherInfo(weatherEntity)
    }
}
