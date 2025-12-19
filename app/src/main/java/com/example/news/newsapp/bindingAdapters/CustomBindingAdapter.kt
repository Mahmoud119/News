package com.example.news.newsapp.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.news.R
@BindingAdapter("app:UrlToImage")
fun imgUrl(imageView: ImageView,url: String?){
    Glide.with(imageView)
        .load(url)
        .error(R.drawable.img)
        .into(imageView)
}