package com.example.apiresponseapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiresponseapp.R
import com.example.apiresponseapp.model.Photos

class PhotosAdapter(private val photos: List<Photos>): RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>(){

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val placeholderImageView: ImageView = itemView.findViewById(R.id.placeholderImageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_photos, parent, false)
        return PhotosViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo = photos[position]
        holder.titleTextView.text = photo.title
        holder.placeholderImageView.setImageResource(R.drawable.placeholder_image)
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}