package com.example.education.data.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "settings",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)]
)
data class Settings(
    @ColumnInfo(name = "user_id") @PrimaryKey val id: Int,
    val settings1: Boolean,
    val settings2: Boolean,
    val settings3: Boolean,
)
