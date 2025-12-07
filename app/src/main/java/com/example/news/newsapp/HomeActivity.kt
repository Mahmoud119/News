package com.example.news.newsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.news.R
import com.example.news.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportFragmentManager
             .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment())
            .commit()

    }
}