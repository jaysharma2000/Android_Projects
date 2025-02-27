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
import com.example.finalmilestone.adapter.CategoryAdapter
import com.example.finalmilestone.dao.RecipeDao
import com.example.finalmilestone.database.AppDatabase
import com.example.finalmilestone.databinding.FragmentHomeBinding
import com.example.finalmilestone.model.Recipe
import com.example.finalmilestone.model.RecipeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private lateinit var binding: FragmentRecipeListBinding
    private lateinit var adapter: RecipeAdapter
    private lateinit var recipeDao: RecipeDao
    private  var categoryId: Long = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeListBinding.bind(view)
        recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()


        arguments?.let {
            categoryId = RecipeListFragmentArgs.fromBundle(it).categoryId
        }
        adapter = RecipeAdapter{ recipe ->
            val action  = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailsFragment(recipe.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutmanager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        loadRecipes()
    }

    private fun loadRecipes(){
        lifecycleScope.launch(Dispatchers.IO){
            var recipes = recipeDao.getRecipesByCategory(categoryId)

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

            withContext(Dispatchers.Main){
                adapter.submitList(recipes)
            }
        }
    }

}