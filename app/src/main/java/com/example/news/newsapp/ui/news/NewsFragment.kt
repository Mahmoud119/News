package com.example.news.newsapp.ui.news

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.newsapp.api.ApiManager
import com.example.news.newsapp.model.Category
import com.example.news.newsapp.model.NewsResponse.NewsResponse
import com.example.news.newsapp.model.ErrorResponse
import com.example.news.newsapp.model.NewsResponse.News
import com.example.news.newsapp.model.sourcesResponse.Source
import com.example.news.newsapp.model.sourcesResponse.SourcesResponse
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    companion object{
        fun getInstance(category: Category): NewsFragment{
            val fragment = NewsFragment()
            fragment.category = category
            return fragment
        }
    }
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
        loadSources()
    }



    private fun loadSources() {
        // loading status
        showLoading()
        ApiManager.webServices().getSources(category.id).enqueue(object : Callback<SourcesResponse> {
            override fun onResponse( // any request from the server
                call: Call<SourcesResponse?>,
                response: Response<SourcesResponse?>
            ) {
                if (!response.isSuccessful) {
                    Log.d(
                        TAG,
                        "onResponse: response.isSuccessful : ${response.isSuccessful.toString()}"
                    )
                    hideLoading()
                    //handl error view
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    val message = errorResponse?.message ?: "Something Went Wrong"
                    showErrorView(message, onTryAgainClicked = {
                        loadSources()
                    })
                    return
                }
                // suecess you have response with sources
                //show sources
                bindTabLayout(response.body()?.sources)
            }


            override fun onFailure( // couldn't connect to the server
                call: Call<SourcesResponse?>,
                error: Throwable
            ) {
                Log.d(TAG, "onFailure: ")
                showErrorView(
                    error.localizedMessage ?: "Something Went Wrong",
                    onTryAgainClicked = {
                        Log.d(TAG, "onFailure: onTryAgainClicked")
                        loadSources()
                    })
            }


        }
        )
    }

    private fun bindTabLayout(sources: List<Source?>?) {
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
                    val source = tab?.tag as Source?
                    source?.id
                    source?.id?.let { loadNews(it) }
                }


                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source?
                    source?.id
                    source?.id?.let { loadNews(it) }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }
            }
        )
       viewBinding.sourcesTabs.getTabAt(0)?.select()
    }

    private fun loadNews(sourceId: String) {
        showLoading()
        ApiManager.webServices().getNews(source = sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse?>,
                    response: Response<NewsResponse?>
                ) {
                    if (!response.isSuccessful) {
                        Log.d(
                            TAG,
                            "onResponse: response.isSuccessful : ${response.isSuccessful.toString()}"
                        )
                        hideLoading()
                        //handl error view
                        val errorBody = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                        val message = errorResponse?.message ?: "Something Went Wrong"
                        showErrorView(message, onTryAgainClicked = {
                            loadNews(sourceId)
                        })
                        return
                    }
                    hideLoading()
                    bindNewsList(response.body()?.newsList)

                }



                override fun onFailure(
                    call: Call<NewsResponse?>,
                    error: Throwable
                ) {
                    Log.d(TAG, "onFailure: ")
                    showErrorView(
                        error.localizedMessage ?: "Something Went Wrong",
                        onTryAgainClicked = {
                            Log.d(TAG, "onFailure: onTryAgainClicked")
                            loadNews(sourceId)
                        })
                }
            })
    }
    val adapter = NewsAdapter()
    private fun bindNewsList(newsList: List<News?>?) {

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

    private fun showErrorView(errorMessage: String, onTryAgainClicked: () -> Unit) {
        Log.d(TAG, "showErrorView: ")
        viewBinding.errorView.isVisible = true
        viewBinding.loadingView.isVisible = false
        viewBinding.errorMessageTv.text = errorMessage
        viewBinding.tryAgainBtn.setOnClickListener {
            onTryAgainClicked.invoke()
        }
    }
}