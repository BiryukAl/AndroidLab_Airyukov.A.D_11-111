package com.example.education.data.network

import com.example.education.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String,
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByCoord(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): WeatherResponse
}
