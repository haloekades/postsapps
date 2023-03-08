package com.ekades.movieapps.lib.application.preferences

import android.content.Context

object TemanDoaSession {
    private val pref: SharedPreferenceBuilder by lazy {
        SharedPreferenceBuilder(Tags.TEMAN_DOA_PREF_TAG, Context.MODE_PRIVATE)
    }

    fun clearSession() {
        pref.getSharedPreference().edit().clear().commit()
    }

    private const val KEY_PRAYER_SCHEDULE_TODAY = "key_prayer_schedule_today"
    private const val KEY_MASTER_CITY_LIST = "key_master_city_list"

    var prayerScheduleToday: String
        get() = pref.getStringPref(KEY_PRAYER_SCHEDULE_TODAY)
        set(value) = pref.setStringPref(KEY_PRAYER_SCHEDULE_TODAY, value)

    var masterCityList: String
        get() = pref.getStringPref(KEY_MASTER_CITY_LIST)
        set(value) = pref.setStringPref(KEY_MASTER_CITY_LIST, value)
}