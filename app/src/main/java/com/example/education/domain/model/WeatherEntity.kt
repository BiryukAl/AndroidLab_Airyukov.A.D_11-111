package com.example.education.domain.model

data class WeatherEntity(
    var cityName: String,
    val icon: String,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val windSpeed: Double,
    var timeRequest: Long,
) {
    constructor() : this("", "50n", 0, 0, 100.0, -1.0, 0)
}
