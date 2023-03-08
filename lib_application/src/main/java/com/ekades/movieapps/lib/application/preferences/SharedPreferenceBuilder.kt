package com.ekades.movieapps.lib.application.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ekades.movieapps.lib.application.ApplicationProvider

/*
    Builder for Shared Preference, based by Shared Preference Tag
*/
class SharedPreferenceBuilder(tag: String, mode: Int) {
    private val context = ApplicationProvider.context
    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences(tag, mode)
    }

    /*
     Add Other Data Type Below as NEEDED
    */

    fun getSharedPreference() = pref
    
    fun getBooleanPref(key: String, default: Boolean = false): Boolean {
        return pref.getBoolean(key, default)
    }

    fun setBooleanPref(key: String, value: Boolean) {
        pref.edit { putBoolean(key, value) }
    }

    fun getStringPref(key: String, default: String = ""): String {
        return pref.getString(key, default).orEmpty()
    }

    fun setStringPref(key: String, value: String) {
        pref.edit { putString(key, value) }
    }

    fun getIntPref(key: String, default: Int = 0): Int {
        return pref.getInt(key, default)
    }

    fun setIntPref(key: String, value: Int) {
        pref.edit { putInt(key, value) }
    }

    fun getLongPref(key: String, default: Long = 0L): Long {
        return pref.getLong(key, default)
    }

    fun setLongPref(key: String, value: Long) {
        pref.edit { putLong(key, value) }
    }
}