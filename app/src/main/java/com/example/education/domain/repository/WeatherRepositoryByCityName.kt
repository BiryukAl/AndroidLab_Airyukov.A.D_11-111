package com.example.education.domain.repository

import com.example.education.domain.model.WeatherEntity

interface WeatherRepositoryByCityName {
    suspend fun getWeatherInfo(city: String): WeatherEntity?
}