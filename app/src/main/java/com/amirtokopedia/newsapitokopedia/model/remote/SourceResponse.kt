package com.amirtokopedia.newsapitokopedia.model.remote

import com.google.gson.annotations.SerializedName

/**
 * Created by Amir Malik on 1/23/18.
 */
class SourceResponse : CoreResponse() {
    @SerializedName("sources")
    var sources: List<Source>? = null
}