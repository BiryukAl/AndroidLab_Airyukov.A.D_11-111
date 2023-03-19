package com.example.education.data.network.model


import android.util.Log
import com.example.education.domain.model.WeatherEntity
import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("coord")
    val coord: Coord?, // Координаты
    @SerializedName("main")
    val main: Main?, // Параметры
    @SerializedName("name")
    val cityName: String?, // Город
    @SerializedName("weather")
    val weather: List<Weather>?, // Погода
    @SerializedName("wind")
    val wind: Wind?, // Ветер
)

data class Wind(
    @SerializedName("speed")
    val speed: Double?,
)

data class Weather(
    @SerializedName("icon")
    val icon: String?,
)

data class Main(
    @SerializedName("humidity")
    val humidity: Int?, // Влажность
    @SerializedName("pressure")
    val pressure: Int?, // Давление
    @SerializedName("temp")
    val temp: Double?,
)

data class Coord(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
)

fun WeatherResponse.mapToWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        cityName ?: "",
        weather?.first()?.icon ?: "50n",
        main?.humidity ?: 0,
        main?.pressure ?: 0,
        main?.temp ?: 100.0,
        wind?.speed ?: -1.0,
        0,
    )
}
