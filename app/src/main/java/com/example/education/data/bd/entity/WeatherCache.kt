package com.example.education.data.bd.entity

import android.os.SystemClock
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.education.domain.model.WeatherEntity


@Entity(tableName = "weather_cache")
data class WeatherCache(
    @ColumnInfo(name = "name_city")
    @PrimaryKey
    val nameCity: String,
    @ColumnInfo(name = "time_request")
    val timeRequest: Long,
    val temp: Double?,
    val humidity: Int?,
    val pressure: Int?,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double?,
    val icon: String?,
)

fun WeatherCache.mapToWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        nameCity,
        icon ?: "50n",
        humidity ?: 0,
        pressure ?: 0,
        temp ?: 100.0,
        windSpeed ?: -1.0,
        timeRequest
    )
}

fun WeatherEntity.mapToWeatherCache(): WeatherCache {
    return WeatherCache(
        cityName.ifBlank { "Unknown" },
        timeRequest,
        temp,
        humidity,
        pressure,
        windSpeed,
        icon
    )
}
