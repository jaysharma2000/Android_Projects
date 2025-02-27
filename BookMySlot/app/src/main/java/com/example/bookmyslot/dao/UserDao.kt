package com.example.bookmyslot.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookmyslot.model.User

//This is a user dao for interviewer login and registration

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username OR email = :email LIMIT 1")
    suspend fun checkIfUserExists(username: String, email: String): User?
}