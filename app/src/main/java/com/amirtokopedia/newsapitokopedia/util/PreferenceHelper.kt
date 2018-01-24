package com.amirtokopedia.newsapitokopedia.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.App

/**
 * Created by Amir Malik on 1/23/18.
 */
object PreferenceHelper {
    /** The [SharedPreferences] object */
    private var context: Context = App.context!!

    /** The [SharedPreferences] object */
    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(PreferenceHelper.context)

    /** The [SharedPreferences.Editor] object */
    @SuppressLint("CommitPrefEdits")
    private var prefsEditor: SharedPreferences.Editor = PreferenceHelper.prefs.edit()

    /**
     * Obtain the key for a pref item.
     * @param prefResId the preferences [String] resource
     * @return the pref key
     */
    private fun getPrefKey(prefResId: Int): String {
        return PreferenceHelper.context.getString(prefResId)
    }

    /** Clears all currently saved entries from prefs */
    fun removeAll() {
        PreferenceHelper.prefsEditor.clear()
        PreferenceHelper.prefsEditor.apply()
    }

    /**
     * Saves the locale.
     * @param locale the locale as a String
     */
    fun saveLocale(locale: String) {
        PreferenceHelper.prefsEditor.putString(PreferenceHelper.getPrefKey(R.string.prefs_locale), locale)
        PreferenceHelper.prefsEditor.apply()
    }

    /**
     * Loads the locale.
     * @return the locale if it exists
     */
    fun loadLocale(): String? {
        return PreferenceHelper.prefs.getString(PreferenceHelper.getPrefKey(R.string.prefs_locale), null)
    }

    /** Clears the locale from prefs  */
    fun removeLocale() {
        PreferenceHelper.prefsEditor.remove(PreferenceHelper.getPrefKey(R.string.prefs_locale))
        PreferenceHelper.prefsEditor.apply()
    }

    fun isLaunch(): Boolean {
        return PreferenceHelper.prefs.getBoolean(PreferenceHelper.getPrefKey(R.string.prefs_is_launch), false)
    }

    fun saveIsLaunch(isLaunch: Boolean) {
        PreferenceHelper.prefsEditor.putBoolean(PreferenceHelper.getPrefKey(R.string.prefs_is_launch), isLaunch)
        PreferenceHelper.prefsEditor.apply()
    }


}