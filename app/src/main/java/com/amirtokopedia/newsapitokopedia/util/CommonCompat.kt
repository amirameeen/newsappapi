package com.amirtokopedia.newsapitokopedia.util

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import java.util.*

/**
 * Created by Amir Malik on 1/23/18.
 */
object CommonCompat {

    fun fromHtml(charSequence: CharSequence): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(charSequence.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            return Html.fromHtml(charSequence.toString())
        }
    }

    fun getCurrentSystemLocale(context: Context): Locale {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.resources.configuration.locales.get(0)
        } else {
            return context.resources.configuration.locale
        }
    }
}
