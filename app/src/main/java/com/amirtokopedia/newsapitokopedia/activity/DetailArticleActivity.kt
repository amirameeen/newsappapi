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
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import java.io.File




class DetailArticleActivity : CoreActivity(), View.OnClickListener {


    var webUrl = ""
    var mWebView : WebView? = null

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

        mWebView = WebView(getApplicationContext());
        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.setWebViewClient(WebViewClient())
        mWebView?.loadUrl(webUrl)
        mWebView?.settings?.cacheMode = WebSettings.LOAD_NO_CACHE
        fl_webview.addView(mWebView)
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
                actionBarSetting(true, titlePage, true)
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releaseWebView()
    }

    private fun releaseWebView() {
        fl_webview.removeAllViews()
        mWebView?.settings?.javaScriptEnabled = false
        mWebView?.removeJavascriptInterface("android");
        mWebView?.clearHistory()
        mWebView?.clearCache(true)
        mWebView?.loadUrl("about:blank")
        mWebView?.onPause()
        mWebView?.removeAllViewsInLayout()
        mWebView?.removeAllViews()
        mWebView?.destroyDrawingCache()
        mWebView?.pauseTimers()
        mWebView?.destroy()
        mWebView = null
        clearApplicationCache()
        this@DetailArticleActivity.deleteDatabase("webview.db");
        this@DetailArticleActivity.deleteDatabase("webviewCache.db");

    }

    override fun onDestroy() {
        super.onDestroy()
        releaseWebView()
        Runtime.getRuntime().gc();
    }

    private fun clearApplicationCache() {
        val dir = cacheDir

        if (dir != null && dir.isDirectory) {
            try {
                val stack = ArrayList<File>()

                // Initialise the list
                val children = dir.listFiles()
                for (child in children) {
                    stack.add(child)
                }

                while (stack.size > 0) {
                    val f = stack[stack.size - 1]
                    if (f.isDirectory() === true) {
                        val empty = f.delete()

                        if (empty == false) {
                            val files = f.listFiles()
                            if (files.size != 0) {
                                for (tmp in files) {
                                    stack.add(tmp)
                                }
                            }
                        } else {
                            stack.removeAt(stack.size - 1)
                        }
                    } else {
                        f.delete()
                        stack.removeAt(stack.size - 1)
                    }
                }
            } catch (e: Exception) {
            }

        }
    }
}