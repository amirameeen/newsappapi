package com.amirtokopedia.newsapitokopedia.presenter

import android.content.Context
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by macbookultimate on 12/27/17.
 */

class ListArticlePresenter(var ApplicationContext: Context, var listener: ArticleInterface) : CorePresenter() {

    fun attach(){
        ApiInitiation()
    }

    fun getDataProcess(sourceId : String) {
        listener.onLoadData()

        val dataService = mApiService?.getHeadlineArticle(sourceId, GetApiKey())
        dataService?.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                val StatusCode = response.code()
                if (StatusCode == 200) {
                    listener.onLoadSuccess(response.body())
                } else {
                    listener.onLoadFailure()
                }

            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                listener.onLoadFailure()
            }
        })
    }

    fun getDataSearch(sourceId : String, query : String) {
        listener.onLoadData()

        val dataService = mApiService?.getSearchArticle(sourceId, query, GetApiKey())
        dataService?.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                val StatusCode = response.code()
                if (StatusCode == 200) {
                    listener.onLoadSuccess(response.body())
                } else {
                    listener.onLoadFailure()
                }

            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                listener.onLoadFailure()
            }
        })
    }

    interface ArticleInterface {
        fun onLoadData()
        fun onLoadSuccess(data: ArticlesResponse)
        fun onLoadFailure()
    }
}