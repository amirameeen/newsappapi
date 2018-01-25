package com.amirtokopedia.newsapitokopedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
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
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_search_article.*

class SearchArticleActivity : CoreActivity(), ListArticlePresenter.ArticleInterface,
        ListArticleRecycleAdapter.onItemClick, View.OnClickListener , TextView.OnEditorActionListener{



    var newsId = ""

    companion object {
        val SOURCE_ID = "source_id"
        val TITLE_PAGE = "title_page"
        fun launchIntent(context: Context, sourceId: String, titlePage : String) {
            val intent = Intent(context, SearchArticleActivity::class.java)
            intent.putExtra(SOURCE_ID, sourceId)
            intent.putExtra(TITLE_PAGE, titlePage)
            context.startActivity(intent)
        }
    }

    var presenter : ListArticlePresenter? = null
    var adapter : ListArticleRecycleAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_article)
        back_button.setOnClickListener(this)
        search_layout_button_search.setOnClickListener(this)
        search_layout_button_clear.setOnClickListener(this)
        getExtras()
        et_search_box.setOnEditorActionListener(this)
        swipetorefresh()
    }

    fun swipetorefresh(){
        swiperefresh.setOnRefreshListener(
                SwipeRefreshLayout.OnRefreshListener {
                    searchData(newsId, et_search_box.text.toString())
                    swiperefresh.isRefreshing = false
                }
        )
    }

    fun searchData(sourceId : String, query : String){
        presenter = ListArticlePresenter(this@SearchArticleActivity, this)
        presenter?.attach()
        presenter?.getDataSearch(sourceId, query)
    }

    fun initView(data : ArticlesResponse){

        recycle_search_article.layoutManager = LinearLayoutManager(this@SearchArticleActivity, LinearLayoutManager.VERTICAL, false)
        adapter = ListArticleRecycleAdapter(this@SearchArticleActivity, data)
        adapter?.itemListener = this
        recycle_search_article.adapter = adapter

    }
    fun getExtras() {
        try {
            // get the Intent that started this Activity
            val `in` = intent
            // get the Bundle that stores the data of this Activity
            val dataBundle = `in`.extras
            if (dataBundle != null) {
                newsId = dataBundle.getString(SOURCE_ID)
                val titlePage = dataBundle.getString(TITLE_PAGE)
                actionBarSetting(true)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun onClick(v: View?) {
        when (v) {
            back_button -> finish()
            search_layout_button_search -> {
                searchData(newsId, et_search_box.text.toString())
            }
            search_layout_button_clear -> et_search_box.setText("")
        }
    }

    override fun onLoadData() {
        Common.showProgressDialog(this@SearchArticleActivity)
    }
    override fun onLoadSuccess(data: ArticlesResponse) {
        if(data.articles?.size == 0)
            card_empty.visibility = View.VISIBLE
        else
            card_empty.visibility = View.GONE
        initView(data)
        Common.dismissProgressDialog()
    }

    override fun onLoadFailure() {
        Common.dismissProgressDialog()
        Common.showMessageDialog(this@SearchArticleActivity, "Please try again")
    }

    override fun onItemSelected(item: ArticlesResponse.dataArticle) {
//        Toast.makeText(this@ListArticleActivity, item.title, Toast.LENGTH_SHORT).show()
        DetailArticleActivity.launchIntent(this@SearchArticleActivity, item.url!!, item.title!!)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when(v){
            et_search_box->{
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchData(newsId, et_search_box.text.toString())
                    return true
                }
                return false
            }
        }
        return false
    }

    public override fun onDestroy() {
        super.onDestroy()
        Runtime.getRuntime().gc()
    }

}