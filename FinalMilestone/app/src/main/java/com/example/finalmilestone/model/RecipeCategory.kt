package com.example.finalmilestone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_categories")
data class RecipeCategory (
    @PrimaryKey(autoGenerate = true)
    val id : Long  = 0,
    val name: String
)