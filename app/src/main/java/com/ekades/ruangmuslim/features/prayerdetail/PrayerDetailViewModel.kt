package com.ekades.ruangmuslim.features.prayerdetail

import android.content.Intent
import com.ekades.ruangmuslim.features.prayerdetail.PrayerDetailActivity.Companion.KEY_EXTRA_JSON_FILE
import com.ekades.ruangmuslim.features.prayerdetail.PrayerDetailActivity.Companion.KEY_EXTRA_POSITION
import com.ekades.ruangmuslim.features.prayerdetail.PrayerDetailActivity.Companion.KEY_EXTRA_PRAYER_TYPE
import com.ekades.ruangmuslim.features.prayerdetail.model.PrayerItem
import com.ekades.ruangmuslim.lib.application.ApplicationProvider
import com.ekades.ruangmuslim.lib.application.viewmodel.BaseViewModel
import com.ekades.ruangmuslim.lib.application.viewmodel.mutableLiveDataOf
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import java.lang.Exception
import java.util.*

class PrayerDetailViewModel : BaseViewModel() {
    val itemList = mutableLiveDataOf<List<PrayerItem>>()
    var currentPosition = 0
    val toolbarTitle = mutableLiveDataOf<String>()

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(Date::class.java, DateTypeAdapter()).create()


    fun processIntent(intent: Intent) {
        if (intent.hasExtra(KEY_EXTRA_PRAYER_TYPE)) {
            toolbarTitle.value = intent.getStringExtra(KEY_EXTRA_PRAYER_TYPE)
        }

        if (intent.hasExtra(KEY_EXTRA_POSITION)) {
            currentPosition = intent.getIntExtra(KEY_EXTRA_POSITION, 0)
        }

        if (intent.hasExtra(KEY_EXTRA_JSON_FILE)) {
            intent.getStringExtra(KEY_EXTRA_JSON_FILE)?.takeIf { it.isNotBlank() }?.apply {
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