package com.amirtokopedia.newsapitokopedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.amirtokopedia.newsapitokopedia.App.Companion.context
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.activity.core.CoreActivity
import com.amirtokopedia.newsapitokopedia.adapter.CategoryAdapter
import com.amirtokopedia.newsapitokopedia.adapter.CountryAdapter
import com.amirtokopedia.newsapitokopedia.adapter.LanguageAdapter
import com.amirtokopedia.newsapitokopedia.adapter.SourceRecycleAdapter
import com.amirtokopedia.newsapitokopedia.model.local.CategoryModel
import com.amirtokopedia.newsapitokopedia.model.local.CountryModel
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.amirtokopedia.newsapitokopedia.model.remote.Source
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse
import com.amirtokopedia.newsapitokopedia.presenter.ListArticlePresenter
import com.amirtokopedia.newsapitokopedia.presenter.SourcePresenter
import com.amirtokopedia.newsapitokopedia.util.Common
import com.amirtokopedia.newsapitokopedia.view.HeadlineDialog
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_news_source.*
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_slider.*
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
        CountryAdapter.onItemClick,
        ListArticlePresenter.ArticleInterface, BaseSliderView.OnSliderClickListener{

    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    var mDrawerToggle: ActionBarDrawerToggle? = null
    var presenter : SourcePresenter? = null
    var presenterBanner : ListArticlePresenter? = null
    var adapter : SourceRecycleAdapter? = null
    var adapterLanguage : LanguageAdapter? = null
    var adapterCategory : CategoryAdapter? = null
    var adapterCountry : CountryAdapter? = null
    var languageOpen = false
    var categoryOpen = false
    var countryOpen = false
    var categoryName = ""
    var languageName = ""
    var countryName = ""
    var countryCode = ""
    var currentCheckCategory : View? = null
    var currentCheckLanguage : View? = null
    var currentCheckCountry : View? = null
    var countryId = "us"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBarSetting(false)
        button_menu.setOnClickListener(this)
//        rl_headline_source.setOnClickListener(this)
        drawerSetting()
        initBanner()
        initData()
        categoryAndLanguage()
        swipetorefresh()
    }

    fun swipetorefresh(){
        swiperefresh.setOnRefreshListener(
                SwipeRefreshLayout.OnRefreshListener {
                    initData()
//                    initBanner()
                    swiperefresh.isRefreshing = false
                }
        )
    }

    fun initData(){
        presenter = SourcePresenter(this@MainActivity, this)
        presenter?.attach()
        presenter?.getDataProcess(countryCode, languageName, categoryName)
        tv_source_name.text = countryName
        if(countryName.equals("All", true) || countryName == "")
        {
            tv_source_name.text = "All Country"
        }
    }

    fun initBanner(){
        presenterBanner = ListArticlePresenter(this@MainActivity, this)
        presenterBanner?.attach()
        presenterBanner?.getDataProcess("", countryId)
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
                    iv_icon_category.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_right_black))
                }else{
                    categoryOpen = true
                    recycler_category.visibility = View.VISIBLE
                    iv_icon_category.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black))
                }
            }
            rl_language->{
                if(languageOpen){
                    languageOpen = false
                    recycler_language.visibility = View.GONE
                    iv_icon_language.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_right_black))
                }else{
                    languageOpen = true
                    recycler_language.visibility = View.VISIBLE
                    iv_icon_language.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black))
                }
            }
            rl_country->{
                if(countryOpen){
                    countryOpen = false
                    recycler_country.visibility = View.GONE
                    iv_icon_country.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_right_black))
                }else{
                    countryOpen = true
                    recycler_country.visibility = View.VISIBLE
                    iv_icon_country.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black))
                }
            }
//            rl_headline_source->{
//                HeadlineDialog(this@MainActivity).show()
//            }
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
        Common.showProgressDialog(this@MainActivity)
    }

    override fun onLoadSuccess(data: SourceResponse) {
        if(data.sources?.size == 0)
            card_empty.visibility = View.VISIBLE
        else
            card_empty.visibility = View.GONE
        initView(data)
        Common.dismissProgressDialog()
        placeholder_news_source.visibility = View.GONE
    }

    override fun onLoadFailure() {
        Common.dismissProgressDialog()
        Common.showMessageDialog(this@MainActivity, "Please try again")
    }

    override fun onItemSelected(item: Source) {
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
        language_value.text = item.name!!
        if(currentCheckLanguage != null)
        {
            tempView.visibility = View.VISIBLE
            currentCheckLanguage?.visibility = View.GONE
            currentCheckLanguage = tempView
        }
        initData()
        drawer_layout.closeDrawers()
        closeDropdown()
    }

    override fun onItemSelected(item: CategoryModel.dataCategory, tempView: View) {
        categoryName = item.code!!
        category_value.text = item.name!!
        if(currentCheckCategory != null)
        {
            tempView.visibility = View.VISIBLE
            currentCheckCategory?.visibility = View.GONE
            currentCheckCategory = tempView
        }
        initData()
        drawer_layout.closeDrawers()
        closeDropdown()

    }

    override fun onItemSelectedCountry(item: CountryModel.dataCountry, tempView: View) {
        countryCode = item.code!!
        country_value.text = item.name!!
        countryName = item.name!!
        if(currentCheckCountry != null)
        {
            tempView.visibility = View.VISIBLE
            currentCheckCountry?.visibility = View.GONE
            currentCheckCountry = tempView
        }
        initData()
        drawer_layout.closeDrawers()
        closeDropdown()

    }

    override fun setCurrentCategory(view: View, name : String) {
        currentCheckCategory = view
        category_value.text = name
    }

    override fun setCurrentLanguage(view: View, name : String) {
        currentCheckLanguage = view
        language_value.text = name
    }

    override fun setCurrentCountry(view: View, name : String) {
        currentCheckCountry = view
        country_value.text = name
    }

    public override fun onDestroy() {
        super.onDestroy()
        Runtime.getRuntime().gc()
    }

    private fun closeDropdown(){
        categoryOpen = false
        recycler_category.visibility = View.GONE
        iv_icon_category.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_right_black))

        countryOpen = false
        recycler_country.visibility = View.GONE
        iv_icon_country.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_right_black))

        languageOpen = false
        recycler_language.visibility = View.GONE
        iv_icon_language.setImageDrawable(this@MainActivity.resources.getDrawable(R.drawable.ic_keyboard_arrow_right_black))

    }

    override fun onLoadSuccess(data: ArticlesResponse) {
        if(data != null){
            var index = 0
            while(index < 5){
                val textSliderView = TextSliderView(this@MainActivity)
                textSliderView
                        .description(data.articles!![index].title)
                        .image(data.articles!![index].urlToImage)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this)

                val mBundle = Bundle()
                mBundle.putString("url", data.articles!![index].url)
                mBundle.putString("title", data.articles!![index].title)
                textSliderView.bundle(mBundle)
                slider.addSlider(textSliderView)
                index++
            }

            slider.setPresetTransformer(SliderLayout.Transformer.Default)
            slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
            slider.setDuration(2000)
        }
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        DetailArticleActivity.launchIntent(this@MainActivity,
                slider?.bundle?.getString("url").toString(), slider?.bundle?.getString("title").toString())
    }

}