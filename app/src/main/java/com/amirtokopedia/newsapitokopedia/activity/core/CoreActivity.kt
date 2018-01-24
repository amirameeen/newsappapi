package com.amirtokopedia.newsapitokopedia.activity.core

import android.content.Context
import android.content.res.Resources
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.util.CommonCompat
import com.amirtokopedia.newsapitokopedia.util.Config
import com.amirtokopedia.newsapitokopedia.util.ConfigurationWrapper
import com.amirtokopedia.newsapitokopedia.util.PreferenceHelper
import com.tsengvn.typekit.TypekitContextWrapper
import kotlinx.android.synthetic.main.action_bar_layout.*
import java.util.*

/**
 * Created by Amir Malik on 1/23/18.
 */
abstract class CoreActivity : AppCompatActivity() {
    /** The current activity locale */
    var currentLocale: String? = null
    protected var curLocale: String? = null

    protected var englishLocale: String? = null
    protected var indonesianLocale: String? = null
    @CallSuper
    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) {
            super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
            return
        }
        englishLocale = newBase.resources.getString(R.string.prefs_locale_en)
        indonesianLocale = newBase.resources.getString(R.string.prefs_locale_in)
        initLocale(newBase, newBase.resources)
        val newContext = ConfigurationWrapper.wrapLocale(newBase, Config.locale!!)
        val newTypekitContext = TypekitContextWrapper.wrap(newContext)
        super.attachBaseContext(newTypekitContext)
    }

    /** Initializes the locale for the activity */
    protected fun initLocale(context: Context, resources: Resources) {
        // Initialize the app's locale
        var curLocale = Config.locale?.toString()
        if (curLocale == null || curLocale.isEmpty()) {
            val savedLocale = PreferenceHelper.loadLocale()
            if (savedLocale == null) {
                val systemLocale = CommonCompat.getCurrentSystemLocale(context)

                // Check default system locale
                if (systemLocale.language == indonesianLocale) curLocale = indonesianLocale
                else curLocale = englishLocale

                // Initial save to prefs
                if (PreferenceHelper.isLaunch()) {
                    PreferenceHelper.saveLocale(curLocale!!)
                }
                this.curLocale = curLocale
            } else {
                if (savedLocale == indonesianLocale) curLocale = indonesianLocale
                else curLocale = englishLocale
            }
        }

        val locale = Locale(curLocale)
        Config.locale = locale
        currentLocale = curLocale
    }

    protected fun changeLanguage(language: String) {
        val languageLocale = language
        var curLocale = languageLocale
        // Initial save to prefs
        PreferenceHelper.saveLocale(curLocale)
        val locale = Locale(curLocale)
        Config.locale = locale
        currentLocale = curLocale
    }



    /** Recreates the activity on locale configuration change */
    protected fun recreateOnLocaleChanges() {
        if (!currentLocale.equals(Config.locale?.toString())) {
            currentLocale = Config.locale?.toString()
            recreate()
        }
    }

    fun actionBarSetting(backButton : Boolean, titlePage : String, isArticle : Boolean) {
        if (backButton)
            back_button.visibility = View.VISIBLE
        else
            back_button.visibility = View.GONE

        if (isArticle) {
            tv_welcome_user.visibility = View.GONE
            tv_article_title.text = titlePage
            tv_article_title.visibility = View.VISIBLE
        } else {
            tv_welcome_user.visibility = View.VISIBLE
            tv_welcome_user.text = titlePage
        }
    }
}