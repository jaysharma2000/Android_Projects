package com.example.bookmyslot.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//This is a interviewSlot table to store all interview slots for particular user
@Parcelize
@Entity(tableName = "interview_slots")
data class InterviewSlot(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val jobRole: String,
    val interviewName: String,
    val designation: String,
    val date: String,
    val time: String
) : Parcelable
