package com.example.education.data.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_cache")
data class WeatherCache(
    @ColumnInfo(name = "name_city")
    @PrimaryKey
    val nameCity: String,
    @ColumnInfo(name = "time_request")
    val timeRequest: Long,
    val temp: Double?,
    val humidity: Int?,
    val pressure:  Int?,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double?,
    val icon: String?
)