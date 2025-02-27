package com.example.finalmilestone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalmilestone.R
import com.example.finalmilestone.dao.RecipeDao
import com.example.finalmilestone.database.AppDatabase
import com.example.finalmilestone.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private lateinit var binding: FragmentRecipeDetailBinding
    //private lateinit var adapter: RecipeAdapter
    private lateinit var recipeDao: RecipeDao
    private  var recipeId: Long = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeDetailBinding.bind(view)
        recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()


        arguments?.let {
            recipeId = RecipeDetailsFragmentArgs.fromBundle(it).recipeId
        }

        loadRecipeDetails()
    }

    private fun loadRecipeDetails(){
        lifecycleScope.launch(Dispatchers.IO){
            var recipe = recipeDao.getRecipeById(recipeId)

            recipe?.let {
                withContext(Dispatchers.Main){
                    binding.titleTextView = it.title
                    binding.ingredientsTextView.text  = it.ingredients
                    binding.instructionsTextView.text  = it.instructions
                    binding.recipeImageView.setImageResource(it.imageRes)
                }
            }

            if(recipes.isEmpty()){
                val dummyRecipe = Recipe(
                    categoryId = categoryId,
                    title = "Sample Recipe",
                    imageRes = R.drawable.ic_launcher_background,
                    ingredients = "Ingredient 1, Ingredient 2",
                    instructions = "this are the instractions to create this recipe"
                )
                recipeDao.insertRecipe(dummyRecipe)
                recipes = recipeDao.getRecipesByCategory(categoryId)

            }
        }
    }
}