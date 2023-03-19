package com.example.education.data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.education.data.bd.entity.Settings
import com.example.education.data.bd.entity.User
import com.example.education.data.bd.entity.WeatherCache
import com.example.education.data.bd.dao.SettingsDao
import com.example.education.data.bd.dao.UserDao
import com.example.education.data.bd.dao.WeatherDao


@Database(entities = [User::class, Settings::class, WeatherCache::class],
    version = DatabaseHandler.databaseVersion)
abstract class InceptionDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getSettingsDao(): SettingsDao
    abstract fun getWeatherDao(): WeatherDao

}
