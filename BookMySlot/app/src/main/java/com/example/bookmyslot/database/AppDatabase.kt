package com.example.bookmyslot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookmyslot.dao.InterviewSlotDao
import com.example.bookmyslot.dao.UserDao
import com.example.bookmyslot.model.InterviewSlot
import com.example.bookmyslot.model.User


//This is a singleton database instance connected with all entities
@Database(entities = [User::class, InterviewSlot::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun interviewSlotDao(): InterviewSlotDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "slot_booking_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}
