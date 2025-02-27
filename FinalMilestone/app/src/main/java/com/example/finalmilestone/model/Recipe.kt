package com.example.finalmilestone.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(entity = RecipeCategory::class, parentColumns =  ["id"], childColumns = ["categoryId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId : Long,
    val title: String,
    val imageRes: Int,
    val ingredients: String,
    val instructions: String
)
