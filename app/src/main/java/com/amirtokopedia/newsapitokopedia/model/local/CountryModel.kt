package com.amirtokopedia.newsapitokopedia.model.local

import com.amirtokopedia.newsapitokopedia.model.remote.Source
import com.google.gson.annotations.SerializedName

/**
 * Created by Amir Malik on 1/24/18.
 */
class CountryModel {
    @SerializedName("countries")
    var countries: List<dataCountry>? = null

    inner class dataCountry{
        @SerializedName("name")
        var  name: Source? = null
        @SerializedName("code")
        var code: String? = null
    }
}