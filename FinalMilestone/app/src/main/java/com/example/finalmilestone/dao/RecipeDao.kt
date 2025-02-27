package com.example.finalmilestone.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.finalmilestone.model.Recipe
import com.example.finalmilestone.model.RecipeCategory

interface RecipeDao {
    @Insert
    suspend fun insertCategory(category: RecipeCategory)

    @Query("SELECT * FROM recipe_categories")
    suspend fun getAllcategories(): List<RecipeCategory>

    @Insert
    suspend fun insertRecipe(recipe: Recipe)
    @Query("SELECT * FROM recipes WHERE categoryId = :categoryId")
    suspend fun getRecipesByCategory(categoryId: Long): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    suspend fun getRecipeById(recipeId: Long): Recipe?

}