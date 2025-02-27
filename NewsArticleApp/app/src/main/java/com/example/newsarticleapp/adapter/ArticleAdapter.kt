package com.example.newsarticleapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsarticleapp.R
import com.example.newsarticleapp.model.ArticleData

class ArticleAdapter(private val articles: List<ArticleData>, private val onClick: (ArticleData) -> Unit):
                                                                                   RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    inner class ArticleViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        var articleName = itemView.findViewById<TextView>(R.id.articleName)
        var articleTitle = itemView.findViewById<TextView>(R.id.articleTitle)

        fun bind(articles: ArticleData){
            articleName.text = "Article Name: ${articles.source.name}"
            articleTitle.text = "Article Title: ${articles.title}"

            itemView.setOnClickListener{onClick(articles)}
        }
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ArticleAdapter.ArticleViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size

     }
}

