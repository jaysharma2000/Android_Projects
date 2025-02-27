package com.example.bookmyslot.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bookmyslot.model.InterviewSlot

//This is a interview slots dao for all crud operations
@Dao
interface InterviewSlotDao {
    @Insert
    suspend fun insertInterviewSlot(slot: InterviewSlot)

    @Query("SELECT * FROM interview_slots WHERE userId = :userId")
    suspend fun getSlotsForUser(userId: Int): List<InterviewSlot>

    @Delete
    suspend fun deleteInterviewSlot(slot: InterviewSlot)

    @Update
    suspend fun updateInterviewSlot(slot: InterviewSlot)
}