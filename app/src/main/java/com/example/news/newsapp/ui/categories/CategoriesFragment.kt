package com.example.news.newsapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.news.databinding.FragmentCategoriesBinding
import com.example.news.newsapp.model.Category

class CategoriesFragment : Fragment() {

    companion object{
        fun getInstance(
            onCategoryClickCallBack: OnCategoryClickCallBack
        ): CategoriesFragment{
            val fragment = CategoriesFragment()
            fragment.onCategoryClickCallBack = onCategoryClickCallBack
            return fragment
        }
    }
    fun  interface OnCategoryClickCallBack{
        fun onCategoryClick(category: Category)
    }
    private var onCategoryClickCallBack : OnCategoryClickCallBack? = null
    lateinit var viewBinding : FragmentCategoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCategoriesBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleerView()
    }
val adapter = CategoriesAdapter(
    onCategoryClick = ::onCategoryClick // pointer to function
)
    fun onCategoryClick(category: Category){
        onCategoryClickCallBack?.onCategoryClick(category)
    }
    private fun initRecycleerView() {
        viewBinding.categoriesRv.adapter = adapter
    }
}