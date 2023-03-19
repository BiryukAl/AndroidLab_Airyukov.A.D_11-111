package com.example.education.domain.repository

import com.example.education.domain.model.WeatherEntity

interface WeatherRepositoryByCoordinate {
    suspend fun getWeatherInfoByCoordinate(lat: Double, lon: Double): WeatherEntity?
}
