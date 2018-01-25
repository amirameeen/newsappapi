package com.amirtokopedia.newsapitokopedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.activity.core.CoreActivity
import com.amirtokopedia.newsapitokopedia.adapter.ListArticleRecycleAdapter
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.amirtokopedia.newsapitokopedia.presenter.ListArticlePresenter
import com.amirtokopedia.newsapitokopedia.util.Common
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_list_article.*

class ListArticleActivity : CoreActivity(), ListArticlePresenter.ArticleInterface, ListArticleRecycleAdapter.onItemClick, View.OnClickListener {


    var sourceId = ""

    companion object {
        val SOURCE_ID = "source_id"
        val TITLE_PAGE = "title_page"
        fun launchIntent(context: Context, sourceId: String, titlePage : String) {
            val intent = Intent(context, ListArticleActivity::class.java)
            intent.putExtra(SOURCE_ID, sourceId)
            intent.putExtra(TITLE_PAGE, titlePage)
            context.startActivity(intent)
        }
    }

    var presenter : ListArticlePresenter? = null
    var adapter : ListArticleRecycleAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_article)
        back_button.setOnClickListener(this)
        search_layout_button.setOnClickListener(this)
        getExtras()
        initData()
        swipetorefresh()
    }

    fun swipetorefresh(){
        swiperefresh.setOnRefreshListener(
                SwipeRefreshLayout.OnRefreshListener {
                    initData()
                    swiperefresh.isRefreshing = false
                }
        )
    }

    fun initData(){
        presenter = ListArticlePresenter(this@ListArticleActivity, this)
        presenter?.attach()
        presenter?.getDataProcess(sourceId, "")
    }

    fun initView(data : ArticlesResponse){

        recycle_list_article.layoutManager = LinearLayoutManager(this@ListArticleActivity, LinearLayoutManager.VERTICAL, false)
        adapter = ListArticleRecycleAdapter(this@ListArticleActivity, data)
        adapter?.itemListener = this
        recycle_list_article.adapter = adapter

    }
    fun getExtras() {
        try {
            // get the Intent that started this Activity
            val `in` = intent
            // get the Bundle that stores the data of this Activity
            val dataBundle = `in`.extras
            if (dataBundle != null) {
                sourceId = dataBundle.getString(SOURCE_ID)
                val titlePage = dataBundle.getString(TITLE_PAGE)
                actionBarSetting(true, titlePage, false)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun onClick(v: View?) {
        when (v) {
            back_button -> finish()
            search_layout_button -> {
                SearchArticleActivity.launchIntent(this@ListArticleActivity, "", "")
            }
        }
    }

    override fun onLoadData() {
        Common.showProgressDialog(this@ListArticleActivity)
    }
    override fun onLoadSuccess(data: ArticlesResponse) {

        if(data.articles?.size == 0)
            card_empty.visibility = View.VISIBLE
        else
            card_empty.visibility = View.GONE

        initView(data)
        Common.dismissProgressDialog()
        placeholder_list_article.visibility = View.GONE
    }

    override fun onLoadFailure() {
        Common.dismissProgressDialog()
        Common.showMessageDialog(this@ListArticleActivity, "Please try again")
    }

    override fun onItemSelected(item: ArticlesResponse.dataArticle) {
//        Toast.makeText(this@ListArticleActivity, item.title, Toast.LENGTH_SHORT).show()
        DetailArticleActivity.launchIntent(this@ListArticleActivity, item.url!!, item.title!!)
    }

    public override fun onDestroy() {
        super.onDestroy()
        Runtime.getRuntime().gc()
    }
}