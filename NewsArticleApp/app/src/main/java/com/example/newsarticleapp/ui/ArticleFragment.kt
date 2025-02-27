package com.example.newsarticleapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsarticleapp.R
import com.example.newsarticleapp.adapter.ArticleAdapter
import com.example.newsarticleapp.api.RetrofitClient
import com.example.newsarticleapp.model.ArticleData
import com.example.newsarticleapp.model.ArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager  = LinearLayoutManager(requireContext())
        fetchArticles()

        return view
    }

    fun fetchArticles(){
            var nameOfSource ="tesla"
            var startDate = "2024-18-12"
            var sortName  = "publishedAt"
            var apiKey = "48b27c2a6e4c4ab2bb806b1b2e38c855"

        RetrofitClient.instance.getArticles(nameOfSource, startDate, sortName, apiKey).enqueue( object : Callback<ArticleResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if(response.isSuccessful){
                   val articles =  response.body()?.articles ?: emptyList()
//                    Log.d(articles.toString(), "onResponse: My news api response")

                    recyclerView.adapter = ArticleAdapter(articles){ selectedArticle ->
                            val action = ArticleFragmentDirections.actionArticleFragmentToArticleDetailsFragment(selectedArticle)
                        recyclerView.adapter?.notifyDataSetChanged()
                        findNavController().navigate(action)
                    }
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to fetch articles", Toast.LENGTH_SHORT).show()
            }
        })
    }
}