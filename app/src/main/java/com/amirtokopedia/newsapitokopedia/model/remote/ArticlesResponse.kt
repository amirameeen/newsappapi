package com.amirtokopedia.newsapitokopedia.model.remote

import com.google.gson.annotations.SerializedName



/**
 * Created by Amir Malik on 1/23/18.
 */
class ArticlesResponse : CoreResponse(){
    @SerializedName("articles")
    var articles: List<dataArticle>? = null

    inner class dataArticle{
        @SerializedName("source")
        var source: Source? = null
        @SerializedName("author")
        var author: String? = null
        @SerializedName("title")
        var title: String? = null
        @SerializedName("description")
        var description: String? = null
        @SerializedName("url")
        var url: String? = null
        @SerializedName("urlToImage")
        var urlToImage: String? = null
        @SerializedName("publishedAt")
        var publishedAt: String? = null
    }
}