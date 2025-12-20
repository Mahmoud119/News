package com.example.news.newsapp.models

import androidx.annotation.DrawableRes
import com.example.news.R

data class Category(
    val id: String,

    val title : String,
    @DrawableRes
    val imgRes : Int
){
    companion object{
        fun getCategories(): List<Category> = listOf(
            Category(
                id = "business",
                title = "Business",
                imgRes = R.drawable.cat_busniess
            ),
            Category(
                id = "entertainment"
                ,title = "entertainment",
                imgRes = R.drawable.cat_entertainment
            ),
            Category(
                id = "general"
                ,title = "general",
                imgRes = R.drawable.cat_general
            ),
            Category(
                id = "health"
                ,title = "health",
                imgRes = R.drawable.cat_helth
            ),         Category(
                id = "science"
                ,title = "science",
                imgRes = R.drawable.cat_science
            ),         Category(
                id = "sports"
                ,title = "sports",
                imgRes = R.drawable.cat_sport
            ),         Category(
                id = "technology"
                ,title = "technology",
                imgRes = R.drawable.cat_technology
            ),

        )
    }
}