package com.example.education.data.bd.dao

import androidx.room.*
import com.example.education.data.bd.entity.Settings

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSettingsUser(settings: Settings)

    @Update
    suspend fun updateUserSettings(settings: Settings)

    @Query("SELECT * FROM settings WHERE user_id = :id")
    suspend fun findById(id: Int): Settings?
}
