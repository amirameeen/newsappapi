package com.amirtokopedia.newsapitokopedia.presenter

import android.graphics.Bitmap
import com.amirtokopedia.newsapitokopedia.api.APIInterface
import com.amirtokopedia.newsapitokopedia.api.APIService
import com.amirtokopedia.newsapitokopedia.util.Config

/**
 * Created by Amir Malik on 1/23/18.
 */
open class CorePresenter {
    var mApiService: APIInterface? = null
    fun ApiInitiation(){
        val service = APIService()
        mApiService = service.getInterfaceService()
    }

    fun GetApiKey() : String{
        return Config.API_KEY
    }
}