package com.amirtokopedia.newsapitokopedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.activity.core.CoreActivity
import com.amirtokopedia.newsapitokopedia.adapter.ListArticleRecycleAdapter
import com.amirtokopedia.newsapitokopedia.adapter.SourceRecycleAdapter
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.amirtokopedia.newsapitokopedia.model.remote.Source
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse
import com.amirtokopedia.newsapitokopedia.presenter.ListArticlePresenter
import com.amirtokopedia.newsapitokopedia.presenter.SourcePresenter
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
        getExtras()
        initData()
    }

    fun initData(){
        presenter = ListArticlePresenter(this@ListArticleActivity, this)
        presenter?.attach()
        presenter?.getDataProcess(sourceId)
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

    override fun onLoadData() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Common.showProgressDialog(this@ListArticleActivity)
    }
    override fun onLoadSuccess(data: ArticlesResponse) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        initView(data)
        Common.dismissProgressDialog()
    }

    override fun onLoadFailure() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Common.dismissProgressDialog()
        Common.showMessageDialog(this@ListArticleActivity, "Please try again")
    }

    override fun onItemSelected(item: ArticlesResponse.dataArticle) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        Toast.makeText(this@ListArticleActivity, item.title, Toast.LENGTH_SHORT).show()
        DetailArticleActivity.launchIntent(this@ListArticleActivity, item.url!!, item.title!!)
    }
}