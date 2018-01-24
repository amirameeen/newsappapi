package com.amirtokopedia.newsapitokopedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.activity.core.CoreActivity
import com.amirtokopedia.newsapitokopedia.adapter.ListArticleRecycleAdapter
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.amirtokopedia.newsapitokopedia.presenter.ListArticlePresenter
import com.amirtokopedia.newsapitokopedia.util.Common

class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            MainActivity.launchIntent(this@SplashActivity)
            finish()
        }, 2000)

    }



}