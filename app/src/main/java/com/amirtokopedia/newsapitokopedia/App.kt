package com.amirtokopedia.newsapitokopedia

import android.content.Context
import android.support.multidex.MultiDexApplication

/**
 * Created by Amir Malik on 1/23/18.
 */
class App : MultiDexApplication() {
    companion object {

        /**
         * The application [Context] made static.
         * Do **NOT** use this as the context for a view,
         * this is mostly used to simplify calling of resources
         * (esp. String resources) outside activities.
         */
        var context: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }




}