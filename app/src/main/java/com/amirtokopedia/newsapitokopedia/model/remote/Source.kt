package com.amirtokopedia.newsapitokopedia.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by Amir Malik on 1/23/18.
 */
class Source {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("category")
    var category: String? = null
    @SerializedName("language")
    var language: String? = null
    @SerializedName("country")
    var country: String? = null

}