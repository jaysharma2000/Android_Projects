package com.example.newsarticleapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData(
    val source: Source,
    val title: String,
    val author: String,
    val description: String,
    val urlToImage: String
): Parcelable

@Parcelize
data class Source(
    val name: String
): Parcelable

@Parcelize
data class ArticleResponse(
    val articles: List<ArticleData>
):Parcelable

