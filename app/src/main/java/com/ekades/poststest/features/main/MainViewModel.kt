package com.ekades.poststest.features.main

import com.ekades.poststest.R
import com.ekades.poststest.features.main.model.MainSection
import com.ekades.poststest.lib.application.ApplicationProvider
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.application.viewmodel.mutableLiveDataOf
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import java.lang.Exception
import java.util.*

class MainViewModel : BaseViewModel() {
    val mainItemList = mutableLiveDataOf<List<MainSection>>()

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(Date::class.java, DateTypeAdapter()).create()

    fun getSectionJson() {
        try {
            val mainSectionJson: String =
                ApplicationProvider.context.assets.open("main-feature-section.json")
                    .bufferedReader()
                    .use { it.readText() }
            setMainItemList(mainSectionJson)
        } catch (e: Exception) {
        }
    }

    private fun setMainItemList(sectionJson: String) {
        try {
            val sectionList = gson.fromJson(sectionJson, Array<MainSection>::class.java).toList()
            mainItemList.value = sectionList.addIcon()
        } catch (e: Exception) {
        }
    }

    private fun List<MainSection>.addIcon(): List<MainSection> {
        this.forEach { section ->
            when (section.id) {
                0 -> section.icon = R.drawable.ic_sunrise
                1 -> section.icon = R.drawable.ic_sunset
                2 -> section.icon = R.drawable.ic_hand_pray
                3 -> section.icon = R.drawable.ic_human_praying_morning
                4 -> section.icon = R.drawable.ic_human_praying_night
                5 -> section.icon = R.drawable.ic_quran
            }
        }

        return this
    }
}