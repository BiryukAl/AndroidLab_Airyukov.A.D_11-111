package com.example.education.data.bd.local.dao

import androidx.room.*
import com.example.education.data.bd.entity.User
import com.example.education.data.bd.model.UserUpdateLogin

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User)

    @Update(entity = User::class)
    suspend fun updateUserLogin(newUser: UserUpdateLogin)

    @Query("SElECT * FROM users WHERE id = :id")
    suspend fun findById(id: Int): User?

    @Query("SElECT * FROM users WHERE login = :login AND password = :password")
    suspend fun findByLoginAndPassword(login: String, password: String): User?

}
