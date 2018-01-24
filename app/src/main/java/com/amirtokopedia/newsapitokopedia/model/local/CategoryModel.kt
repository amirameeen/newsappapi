package com.amirtokopedia.newsapitokopedia.model.local

import com.amirtokopedia.newsapitokopedia.model.remote.Source
import com.google.gson.annotations.SerializedName

/**
 * Created by Amir Malik on 1/24/18.
 */
class CategoryModel {
    @SerializedName("categories")
    var categories: List<dataCategory>? = null

    inner class dataCategory{
        @SerializedName("name")
        var  name: String? = null
        @SerializedName("code")
        var code: String? = null
    }
}