package com.example.education.data.bd.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.education.data.bd.entity.Settings
import com.example.education.data.bd.entity.User
import com.example.education.data.bd.local.dao.SettingsDao
import com.example.education.data.bd.local.dao.UserDao


@Database(entities = [User::class, Settings::class],
    version = DatabaseHandler.databaseVersion)
abstract class InceptionDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getSettingsDao(): SettingsDao

}
