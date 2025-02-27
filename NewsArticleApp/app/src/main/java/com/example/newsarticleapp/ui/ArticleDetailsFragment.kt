package com.example.newsarticleapp.ui

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsarticleapp.R
import com.example.newsarticleapp.model.ArticleData

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_article_details, container, false)
        val article = arguments?.getParcelable<ArticleData>("selectedArticle")

        view.findViewById<TextView>(R.id.articleAuthor).text = Html.fromHtml("<b>Article Author:</b> ${article?.author}")
        view.findViewById<TextView>(R.id.articleDiscription).text = "Article Discription: ${article?.description}"

        val imageView = view.findViewById<ImageView>(R.id.articleImage)
        Glide.with(this).load(article?.urlToImage).into(imageView)

        return view
    }
}

