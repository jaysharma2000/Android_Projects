package com.example.finalmilestone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalmilestone.dao.RecipeDao
import com.example.finalmilestone.model.Recipe
import com.example.finalmilestone.model.RecipeCategory

@Database(entities = [RecipeCategory::class, Recipe::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var  INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_app_db"
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}