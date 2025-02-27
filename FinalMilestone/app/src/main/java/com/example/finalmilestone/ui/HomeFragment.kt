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
import com.example.finalmilestone.model.RecipeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: CategoryAdapter
    private lateinit var recipeDao: RecipeDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()
        adapter = CategoryAdapter{ category ->
            val action  = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment(category.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutmanager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        loadCategories()
    }

    private fun loadCategories(){
        lifecycleScope.launch(Dispatchers.IO){
            var categories = recipeDao.getAllcategories()

            if(categories.isEmpty()){
                val defaultcategories = listOf(
                    RecipeCategory(name = "Breakfast"),
                    RecipeCategory(name = "Lunch"),
                    RecipeCategory(name = "Dinner"),
                    RecipeCategory(name = "Desserts"),

                )
                defaultcategories.forEach{ recipeDao.insertCategory(it)}
                categories = recipeDao.getAllcategories()

            }

            withContext(Dispatchers.Main){
                adapter.submitList(categories)
            }
        }
    }

}