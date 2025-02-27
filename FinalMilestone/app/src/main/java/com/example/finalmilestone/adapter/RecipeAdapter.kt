package com.example.finalmilestone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalmilestone.model.Recipe
import com.example.finalmilestone.model.RecipeCategory

class RecipeAdapter(private val onItemClick: (Recipe) -> Unit) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){

    private var recipes: List<Recipe> = emptyList()
    fun submitlist(list: List<Recipe>){
        recipes = list
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(recipe: Recipe){
            binding.titleTextView.text = recipe.title
            binding.recipeImageView.setImageResource(recipe.imageRes)
            binding.root.setOnClickListener{ onItemClick(recipe)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)

        return  RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }


}