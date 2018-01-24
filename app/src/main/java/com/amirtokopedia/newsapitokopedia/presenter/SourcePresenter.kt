package com.amirtokopedia.newsapitokopedia.presenter

import android.content.Context
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by macbookultimate on 12/27/17.
 */

class SourcePresenter(var ApplicationContext: Context, var listener: SourceInterface) : CorePresenter() {

    fun attach(){
        ApiInitiation()
    }

    fun getDataProcess() {
        listener.onLoadData()

        val dataService = mApiService?.getNewsSource(GetApiKey())
        dataService?.enqueue(object : Callback<SourceResponse> {
            override fun onResponse(call: Call<SourceResponse>, response: Response<SourceResponse>) {
                val StatusCode = response.code()
                if (StatusCode == 200) {
                    listener.onLoadSuccess(response.body())
                } else {
                    listener.onLoadFailure()
                }

            }

            override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                listener.onLoadFailure()
            }
        })
    }

    interface SourceInterface {
        fun onLoadData()
        fun onLoadSuccess(data: SourceResponse)
        fun onLoadFailure()
    }
}
