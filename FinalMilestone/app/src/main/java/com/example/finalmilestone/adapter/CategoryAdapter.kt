package com.example.finalmilestone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalmilestone.databinding.FragmentHomeBinding
import com.example.finalmilestone.databinding.ItemCategoryBinding
import com.example.finalmilestone.model.RecipeCategory

class CategoryAdapter(private val onItemClick: (RecipeCategory) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    private var categories: List<RecipeCategory> = emptyList()
    fun submitlist(list: List<RecipeCategory>){
        categories = list
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: RecipeCategory){
            binding.categoryTectView.text = category.name
            binding.root.setOnClickListener{ onItemClick(category)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),
           parent,false)

        return  CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }


}