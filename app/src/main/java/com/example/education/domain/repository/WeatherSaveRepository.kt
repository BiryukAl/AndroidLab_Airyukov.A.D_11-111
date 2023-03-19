package com.example.education.domain.repository

import com.example.education.domain.model.WeatherEntity

interface WeatherSaveRepository {
    suspend fun addWeatherInfo(weatherEntity: WeatherEntity)
}
