package com.ekades.movieapps.features.prayerlist

import android.content.Intent
import com.ekades.movieapps.features.prayerdetail.model.PrayerItem
import com.ekades.movieapps.lib.application.ApplicationProvider
import com.ekades.movieapps.lib.application.viewmodel.BaseViewModel
import com.ekades.movieapps.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.movieapps.features.prayerlist.PrayerListActivity.Companion.KEY_EXTRA_JSON_FILE
import com.ekades.movieapps.features.prayerlist.PrayerListActivity.Companion.KEY_EXTRA_PRAYER_TYPE
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import java.lang.Exception
import java.util.*

class PrayerListViewModel : BaseViewModel() {
    val itemList = mutableLiveDataOf<List<PrayerItem>>()
    val toolbarTitle = mutableLiveDataOf<String>()
    var jsonFile: String = ""

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(Date::class.java, DateTypeAdapter()).create()


    fun processIntent(intent: Intent) {
        if (intent.hasExtra(KEY_EXTRA_PRAYER_TYPE)) {
            toolbarTitle.value = intent.getStringExtra(KEY_EXTRA_PRAYER_TYPE)
        }

        if (intent.hasExtra(KEY_EXTRA_JSON_FILE)) {
            jsonFile = intent.getStringExtra(KEY_EXTRA_JSON_FILE) ?: ""
            jsonFile.takeIf { it.isNotBlank() }?.apply {
                getJsonFile(this)
            }
        }
    }

    private fun getJsonFile(jsonFile: String) {
        try {
            val mainSectionJson: String =
                ApplicationProvider.context.assets.open(jsonFile)
                    .bufferedReader()
                    .use { it.readText() }
            setMainItemList(mainSectionJson)
        } catch (e: Exception) {
        }
    }

    private fun setMainItemList(sectionJson: String) {
        try {
            val prayers = gson.fromJson(sectionJson, Array<PrayerItem>::class.java).toList()
            itemList.value = prayers
        } catch (e: Exception) {
        }
    }

}