package com.amirtokopedia.newsapitokopedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.layout_main.*

class MainActivity : CoreActivity(), SourcePresenter.SourceInterface, SourceRecycleAdapter.onItemClick,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    var mDrawerToggle: ActionBarDrawerToggle? = null
    var presenter : SourcePresenter? = null
    var adapter : SourceRecycleAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBarSetting(false)
        button_menu.setOnClickListener(this)
        drawerSetting()
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

    override fun onClick(v: View?) {
        when(v){
            button_menu->drawer_layout.openDrawer(navigation_drawer)
        }
    }

    fun drawerSetting(){
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()
        navigation_drawer.setNavigationItemSelectedListener(this)
        drawer_layout.setDrawerListener(mDrawerToggle)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
            return
        }
        super.onBackPressed()
    }
}

