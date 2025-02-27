package com.example.bookmyslot.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//This is a user table to store all interviewer login details
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val password: String
)


