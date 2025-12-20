package com.example.news.newsapp.ui.news

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.newsapp.common.ErrorState
import com.example.news.newsapp.model.Category
import com.example.news.newsapp.model.NewsResponse.NewsDTO
import com.example.news.newsapp.model.sourcesResponse.SourceDTO
import com.google.android.material.tabs.TabLayout


class NewsFragment : Fragment() {
    companion object{
        fun getInstance(category: Category): NewsFragment{
            val fragment = NewsFragment()
            fragment.category = category
            return fragment
        }
    }
    val viewModel : NewsViewModel by viewModels<NewsViewModel>()
   lateinit var category : Category
    lateinit var viewBinding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        initView()
        viewModel.loadSources(categoryId = category.id)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.sourcesLiveData.observe(viewLifecycleOwner, Observer{
            data-> // observer will provide the list of sources that we observe
            bindTabLayout(data)
        })
        viewModel.showLoading.observe(viewLifecycleOwner, Observer{
            isloading->
            if(isloading){
                showLoading()
            }else{
                hideLoading()
            }
        })
        viewModel.errorState.observe(viewLifecycleOwner, Observer{
            errorState -> showErrorView(errorState)
        })
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer{
            newsList->
            
            bindNewsList(newsList)
        })
    }


    private fun bindTabLayout(sources: List<SourceDTO?>?) {
        viewBinding.loadingView.isVisible = false
        viewBinding.errorView.isVisible = false
        sources?.forEach { source ->
            Log.d(TAG, "bindTabLayout: ")
            val tab = viewBinding.sourcesTabs.newTab()
            tab.text = source?.name
            tab.tag = source
            viewBinding.sourcesTabs.addTab(tab)
        }
        viewBinding.sourcesTabs.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourceDTO?
                    source?.id
                    source?.id?.let { viewModel.loadNews(it) }
                }


                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourceDTO?
                    source?.id
                    source?.id?.let { viewModel.loadNews(it) }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }
            }
        )
       viewBinding.sourcesTabs.getTabAt(0)?.select()
    }


    val adapter = NewsAdapter()
    private fun bindNewsList(newsList: List<NewsDTO?>?) {

        adapter.newsList = newsList
        adapter.changeData(newsList)

    }
    private fun initView() {
        viewBinding.newsRecycler.adapter = adapter
    }
    private fun showLoading() {
        Log.d(TAG, "showLoading: ")
        viewBinding.loadingView.isVisible = true
        viewBinding.errorView.isVisible = false
    }

    private fun hideLoading() {
        Log.d(TAG, "hideLoading: ")
        viewBinding.loadingView.isVisible = false
        viewBinding.errorView.isVisible = false
    }

    private fun showErrorView(errorState: ErrorState) {
        Log.d(TAG, "showErrorView: ")
        viewBinding.errorView.isVisible = true
        viewBinding.loadingView.isVisible = false
        viewBinding.errorMessageTv.text = errorState.errorMessage
        viewBinding.tryAgainBtn.setOnClickListener {
            errorState.onRetry?.invoke()
        }
    }
}