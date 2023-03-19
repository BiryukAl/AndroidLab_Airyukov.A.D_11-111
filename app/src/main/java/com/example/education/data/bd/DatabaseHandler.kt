package com.example.education.data.bd

import android.content.Context
import androidx.room.Room


object DatabaseHandler {

    const val databaseVersion: Int = 1

    var roomDatabase: InceptionDatabase? = null

    fun dbInitialize(appContext: Context) {
        if (roomDatabase == null) {
            roomDatabase =
                Room.databaseBuilder(appContext,
                    InceptionDatabase::class.java,
                    "user_data")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}
