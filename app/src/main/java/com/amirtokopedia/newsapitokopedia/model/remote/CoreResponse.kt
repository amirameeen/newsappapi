package com.amirtokopedia.newsapitokopedia.model.remote

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose



/**
 * Created by Amir Malik on 1/23/18.
 */
open class CoreResponse {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("message")
    var message: String = ""
    @SerializedName("totalResults")
    var totalResults: Int = 0
}