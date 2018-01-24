package com.amirtokopedia.newsapitokopedia.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.activity.core.CoreActivity
import com.amirtokopedia.newsapitokopedia.adapter.SourceRecycleAdapter
import com.amirtokopedia.newsapitokopedia.model.remote.Source
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse
import com.amirtokopedia.newsapitokopedia.presenter.SourcePresenter
import com.amirtokopedia.newsapitokopedia.util.Common
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CoreActivity(), SourcePresenter.SourceInterface, SourceRecycleAdapter.onItemClick{



    var presenter : SourcePresenter? = null
    var adapter : SourceRecycleAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
    }

    fun initData(){
        presenter = SourcePresenter(this@MainActivity, this)
        presenter?.attach()
        presenter?.getDataProcess()
    }

    fun initView(data : SourceResponse){

        recycle_source_news.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapter = SourceRecycleAdapter(this@MainActivity, data)
        adapter?.itemListener = this
        recycle_source_news.adapter = adapter

    }


    override fun onLoadData() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Common.showProgressDialog(this@MainActivity)
    }

    override fun onLoadSuccess(data: SourceResponse) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        initView(data)
        Common.dismissProgressDialog()
        placeholder_news_source.visibility = View.GONE
    }

    override fun onLoadFailure() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Common.dismissProgressDialog()
        Common.showMessageDialog(this@MainActivity, "Please try again")
    }

    override fun onItemSelected(item: Source) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        Toast.makeText(this@MainActivity, item.name, Toast.LENGTH_SHORT).show()
        ListArticleActivity.launchIntent(this@MainActivity, item.id!!, item.name!!)
    }
}

