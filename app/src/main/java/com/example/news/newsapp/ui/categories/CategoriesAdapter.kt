package com.example.news.newsapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemCatBinding
import com.example.news.newsapp.model.Category

class CategoriesAdapter(var items: List<Category> = Category.getCategories(),
    val onCategoryClick : ((categoty: Category)-> Unit)
    ): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onCategoryClick(items[position])
        }
    }

    override fun getItemCount(): Int = items.size // if null return 0

    class ViewHolder(val binding : ItemCatBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category?){
            binding.categoryImgv.setImageResource(category!!.imgRes )
        }
    }

}