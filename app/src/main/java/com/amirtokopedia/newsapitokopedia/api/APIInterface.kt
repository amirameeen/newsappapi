package com.amirtokopedia.newsapitokopedia.api


import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Amir Malik on 5/2/17.
 */

interface APIInterface {
    @GET("sources")
    fun getNewsSource(@Query("apiKey") ApiKey: String): Call<SourceResponse>

    @GET("top-headlines")
    fun getHeadlineArticle(@Query("sources") sourceId : String,
                           @Query("apiKey") ApiKey: String): Call<ArticlesResponse>
}