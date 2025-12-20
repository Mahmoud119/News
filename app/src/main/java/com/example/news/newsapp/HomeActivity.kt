package com.example.news.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.news.R
import com.example.news.databinding.ActivityHomeBinding
import com.example.news.newsapp.models.Category
import com.example.news.newsapp.ui.categories.CategoriesFragment
import com.example.news.newsapp.ui.news.NewsFragment

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        showMainFragment(CategoriesFragment.getInstance (
            onCategoryClickCallBack = :: onCategoryClick
        ))

        setSupportActionBar(viewBinding.appBarActivityHome.toolbar)
        viewBinding.appBarActivityHome.toolbar.setNavigationOnClickListener {
            viewBinding.drawerLayout.open()
        }
        viewBinding.navView.setNavigationItemSelectedListener {item ->
            when (item.itemId){
                R.id.nav_home -> {
                    showMainFragment(CategoriesFragment.getInstance (
                        onCategoryClickCallBack = :: onCategoryClick
                    ))
                }
                else -> {

                }
            }
            viewBinding.drawerLayout.close()
            return@setNavigationItemSelectedListener true
        }

        }

    private fun onCategoryClick(category: Category){
        showNewsFragment(category)
    }

    private fun showNewsFragment(category: Category) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.getInstance(
                category = category
            ))
            .commit()
    }

    private fun showMainFragment(fragment: CategoriesFragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}
