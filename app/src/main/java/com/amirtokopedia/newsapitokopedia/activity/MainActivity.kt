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
import com.amirtokopedia.newsapitokopedia.App.Companion.context
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.activity.core.CoreActivity
import com.amirtokopedia.newsapitokopedia.adapter.CategoryAdapter
import com.amirtokopedia.newsapitokopedia.adapter.CountryAdapter
import com.amirtokopedia.newsapitokopedia.adapter.LanguageAdapter
import com.amirtokopedia.newsapitokopedia.adapter.SourceRecycleAdapter
import com.amirtokopedia.newsapitokopedia.model.local.CategoryModel
import com.amirtokopedia.newsapitokopedia.model.local.CountryModel
import com.amirtokopedia.newsapitokopedia.model.remote.Source
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse
import com.amirtokopedia.newsapitokopedia.presenter.SourcePresenter
import com.amirtokopedia.newsapitokopedia.util.Common
import com.google.gson.Gson
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.list_category.*
import kotlinx.android.synthetic.main.list_country.*
import kotlinx.android.synthetic.main.list_language.*
import java.io.IOException
import java.io.InputStream

class MainActivity : CoreActivity(), SourcePresenter.SourceInterface, SourceRecycleAdapter.onItemClick,
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        LanguageAdapter.onItemClick,
        CategoryAdapter.onItemClick,
        CountryAdapter.onItemClick{



    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    var mDrawerToggle: ActionBarDrawerToggle? = null
    var presenter : SourcePresenter? = null
    var adapter : SourceRecycleAdapter? = null
    var adapterLanguage : LanguageAdapter? = null
    var adapterCategory : CategoryAdapter? = null
    var adapterCountry : CountryAdapter? = null
    var languageOpen = true
    var categoryOpen = true
    var countryOpen = true
    var categoryName = ""
    var languageName = ""
    var countryName = ""
    var currentCheckCategory : View? = null
    var currentCheckLanguage : View? = null
    var currentCheckCountry : View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBarSetting(false)
        button_menu.setOnClickListener(this)
        drawerSetting()
        initData()
        categoryAndLanguage()
    }

    fun initData(){
        presenter = SourcePresenter(this@MainActivity, this)
        presenter?.attach()
        presenter?.getDataProcess(countryName, languageName, categoryName)
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
            rl_category->{
                if(categoryOpen){
                    categoryOpen = false
                    recycler_category.visibility = View.GONE
                }else{
                    categoryOpen = true
                    recycler_category.visibility = View.VISIBLE
                }
            }
            rl_language->{
                if(languageOpen){
                    languageOpen = false
                    recycler_language.visibility = View.GONE
                }else{
                    languageOpen = true
                    recycler_language.visibility = View.VISIBLE
                }
            }
            rl_country->{
                if(countryOpen){
                    countryOpen = false
                    recycler_country.visibility = View.GONE
                }else{
                    countryOpen = true
                    recycler_country.visibility = View.VISIBLE
                }
            }

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

    fun categoryAndLanguage(){
        var jsonCategory = ""
        var jsonLanguage = ""
        var jsonCountry = ""
        val menuCategory : InputStream
        val menuLanguage : InputStream
        val menuCountry : InputStream
        try {
            menuCategory = context!!.assets.open("category.json")
            menuLanguage = context!!.assets.open("language.json")
            menuCountry = context!!.assets.open("country.json")


            val sizeCategory = menuCategory.available()
            val bufferCategory = ByteArray(sizeCategory)
            menuCategory.read(bufferCategory)
            menuCategory.close()

            val sizeLanguage = menuLanguage.available()
            val bufferLanguage = ByteArray(sizeLanguage)
            menuLanguage.read(bufferLanguage)
            menuLanguage.close()

            val sizeCountry = menuCountry.available()
            val bufferCountry = ByteArray(sizeCountry)
            menuCountry.read(bufferCountry)
            menuCountry.close()

            jsonCategory = String(bufferCategory, Charsets.UTF_8)
            jsonLanguage = String(bufferLanguage, Charsets.UTF_8)
            jsonCountry = String(bufferCountry, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        var dataCategory: CategoryModel = Gson().fromJson(jsonCategory, CategoryModel::class.java)
        var dataLanguage: CountryModel = Gson().fromJson(jsonLanguage, CountryModel::class.java)
        var dataCountry: CountryModel = Gson().fromJson(jsonCountry, CountryModel::class.java)


        recycler_language.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapterLanguage = LanguageAdapter(this@MainActivity, dataLanguage)
        adapterLanguage?.itemListener = this
        recycler_language.adapter = adapterLanguage

        recycler_category.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapterCategory = CategoryAdapter(this@MainActivity, dataCategory)
        adapterCategory?.itemListener = this
        recycler_category.adapter = adapterCategory

        recycler_country.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapterCountry = CountryAdapter(this@MainActivity, dataCountry)
        adapterCountry?.itemListener = this
        recycler_country.adapter = adapterCountry


        rl_category.setOnClickListener(this)
        rl_language.setOnClickListener(this)
        rl_country.setOnClickListener(this)
    }

    override fun onItemSelected(item: CountryModel.dataCountry, tempView: View) {
        languageName = item.code!!
        if(currentCheckLanguage != null)
        {
            tempView.visibility = View.VISIBLE
            currentCheckLanguage?.visibility = View.GONE
            currentCheckLanguage = tempView
        }
        initData()
        drawer_layout.closeDrawers()
    }

    override fun onItemSelected(item: CategoryModel.dataCategory, tempView: View) {
        categoryName = item.code!!
        if(currentCheckCategory != null)
        {
            tempView.visibility = View.VISIBLE
            currentCheckCategory?.visibility = View.GONE
            currentCheckCategory = tempView
        }
        initData()
        drawer_layout.closeDrawers()

    }

    override fun onItemSelectedCountry(item: CountryModel.dataCountry, tempView: View) {
        countryName = item.code!!
        if(currentCheckCountry != null)
        {
            tempView.visibility = View.VISIBLE
            currentCheckCountry?.visibility = View.GONE
            currentCheckCountry = tempView
        }
        initData()
        drawer_layout.closeDrawers()

    }

    override fun setCurrentCategory(view: View) {
        currentCheckCategory = view
    }

    override fun setCurrentLanguage(view: View) {
        currentCheckLanguage = view
    }

    override fun setCurrentCountry(view: View) {
        currentCheckCountry = view
    }

    public override fun onDestroy() {
        super.onDestroy()
        Runtime.getRuntime().gc()
    }
}

