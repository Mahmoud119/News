package com.example.news.newsapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.News
import com.example.news.databinding.ItemNewsBinding

class NewsAdapter(var newsList: List<News?>? =null): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList?.get(position))
    }

    override fun getItemCount(): Int = newsList?.size ?: 0 // if null return 0

    class ViewHolder(val binding : ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: News?){
            binding.nesItem= news
            binding.executePendingBindings()


         }
    }

    fun changeData( newslist : List<News?>?){
        this.newsList = newslist
        notifyDataSetChanged()
    }
}