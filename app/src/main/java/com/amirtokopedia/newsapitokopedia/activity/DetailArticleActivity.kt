package com.amirtokopedia.newsapitokopedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.activity.core.CoreActivity
import com.amirtokopedia.newsapitokopedia.adapter.ListArticleRecycleAdapter
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.amirtokopedia.newsapitokopedia.presenter.ListArticlePresenter
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_detail_article.*

class DetailArticleActivity : CoreActivity(), View.OnClickListener {


    var webUrl = ""

    companion object {
        val WEB_URL = "weburl"
        val TITLE_PAGE = "titlePage"
        fun launchIntent(context: Context, webUrl: String, titlePage : String) {
            val intent = Intent(context, DetailArticleActivity::class.java)
            intent.putExtra(WEB_URL, webUrl)
            intent.putExtra(TITLE_PAGE, titlePage)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
        back_button.setOnClickListener(this)
        getExtras()
        initView()
    }

    fun initView(){
        webview_content.settings.javaScriptEnabled = true
        webview_content.setWebViewClient(WebViewClient())
        webview_content.loadUrl(webUrl)
    }
    fun getExtras() {
        try {
            // get the Intent that started this Activity
            val `in` = intent
            // get the Bundle that stores the data of this Activity
            val dataBundle = `in`.extras
            if (dataBundle != null) {
                webUrl = dataBundle.getString(WEB_URL)
                val titlePage = dataBundle.getString(TITLE_PAGE)
                actionBarSetting(true, titlePage)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if(v == back_button){
            finish()
        }
    }
}