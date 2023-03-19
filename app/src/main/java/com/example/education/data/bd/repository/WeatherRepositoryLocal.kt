package com.example.education.data.bd.repository

import com.example.education.data.bd.InceptionDatabase
import com.example.education.data.bd.entity.mapToWeatherCache
import com.example.education.data.bd.entity.mapToWeatherEntity
import com.example.education.domain.model.WeatherEntity
import com.example.education.domain.repository.WeatherRepositoryByCityName
import com.example.education.domain.repository.WeatherSaveRepository

class WeatherRepositoryLocal(
    private val localSource: InceptionDatabase,
) : WeatherRepositoryByCityName,
    WeatherSaveRepository {

    override suspend fun getWeatherInfo(city: String): WeatherEntity? {
        return localSource.getWeatherDao().findByCityName(city)
            ?.mapToWeatherEntity()
    }

    override suspend fun addWeatherInfo(weatherEntity: WeatherEntity) {
        localSource.getWeatherDao()
            .addByCityName(weatherEntity.mapToWeatherCache())
    }
}
