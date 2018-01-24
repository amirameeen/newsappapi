package com.amirtokopedia.newsapitokopedia.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.*

/**
 * Created by Amir Malik on 1/23/18.
 */
object ConfigurationWrapper {

    /**
     * Creates a [Context] with updated [Configuration]
     * @return the [Context]
     */
    fun wrapConfiguration(context: Context, config: Configuration): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.createConfigurationContext(config)
        } else {
            return context
        }
    }

    /**
     * Creates a [Context] with updated [Locale]
     * @return the [Context]
     */
    fun wrapLocale(context: Context, locale: Locale): Context {
        val res = context.resources
        val config = res.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.locales = localeList
            config.setLocale(locale)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
        } else {
            config.locale = locale
            res.updateConfiguration(config, null)
        }

        return com.amirtokopedia.newsapitokopedia.util.ConfigurationWrapper.wrapConfiguration(context, config)
    }
}