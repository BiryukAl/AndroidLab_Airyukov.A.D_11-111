package com.example.education.data.bd.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users",
    indices = [
        Index("login", unique = true)
    ])
data class User(
    val login: String,
    val password: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
