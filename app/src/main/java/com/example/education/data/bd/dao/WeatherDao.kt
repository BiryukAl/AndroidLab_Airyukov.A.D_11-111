package com.example.education.data.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.education.data.bd.entity.WeatherCache

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addByCityName(weatherCache: WeatherCache)

    @Query("SELECT * FROM weather_cache WHERE name_city = :nameCity")
    suspend fun findByCityName(nameCity: String): WeatherCache?
}