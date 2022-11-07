package com.ekades.temandoa.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekades.temandoa.R
import com.ekades.temandoa.features.main.model.MainSection
import com.ekades.temandoa.features.main.state.PrayerScheduleTodayVS
import com.ekades.temandoa.networksV2.prayerschedule.domain.interactor.GetPrayerScheduleTodayInteractor
import com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleToday
import com.ekades.temandoa.lib.application.ApplicationProvider
import com.ekades.temandoa.lib.application.preferences.TemanDoaSession
import com.ekades.temandoa.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.temandoa.lib.core.NetworkUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class MainViewModel(
    private val getPrayerScheduleTodayInteractor: GetPrayerScheduleTodayInteractor
) : ViewModel() {

    val viewState: LiveData<PrayerScheduleTodayVS> get() = mViewState
    private val mViewState = MutableLiveData<PrayerScheduleTodayVS>()

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
                0 -> section.icon = R.drawable.ic_quran
                1 -> section.icon = R.drawable.ic_headphone
                2 -> section.icon = R.drawable.ic_sunrise
                3 -> section.icon = R.drawable.ic_sunset
                4 -> section.icon = R.drawable.ic_hand_pray
                5 -> section.icon = R.drawable.ic_human_praying_morning
                6 -> section.icon = R.drawable.ic_human_praying_night
//                6 -> section.icon = R.drawable.ic_quran
            }
        }

        return this
    }

    private fun getPrayerScheduleToday(
        cityId: String, year: String, month: String, day: String,
        onFailedListener: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                showLoader(true)
                getPrayerScheduleTodayInteractor.execute(
                    GetPrayerScheduleTodayInteractor.Params(
                        cityId, year, month, day
                    )
                ).collect {
                    if (it.status && it.data != null) {
                        val prayerScheduleToday = it.data.also { prayerScheduleToday ->
                            prayerScheduleToday.requestDate = it.data.jadwal?.date
                        }

                        onSuccess(prayerScheduleToday)
                        TemanDoaSession.prayerScheduleToday = prayerScheduleToday.toJson()
                    } else {
                        onFailedListener?.invoke()
                        onFailed(it.message.orEmpty())
                    }
                }
            } catch (e: Exception) {
                onFailedListener?.invoke()
                onFailed(e.message.orEmpty())
            }
        }
    }

    private fun getSelectedTime(
        result: (year: String, month: String, day: String) -> Unit
    ) = viewModelScope.launch {
        try {
            val dt = Date()
            val c = Calendar.getInstance()
            c.time = dt
            result.invoke(
                c.get(Calendar.YEAR).toString(),
                (c.get(Calendar.MONTH) + 1).toString(),
                c.get(Calendar.DATE).toString()
            )
        } catch (e: Exception) {
        }
    }

    private fun onSuccess(prayerScheduleToday: PrayerScheduleToday) {
        mViewState.value = PrayerScheduleTodayVS.ShowPrayerScheduleToday(prayerScheduleToday)
        showLoader(false)
    }

    private fun onFailed(errorMessage: String) {
        mViewState.value = PrayerScheduleTodayVS.Error(errorMessage)
        showLoader(false)
    }

    private fun showLoader(isShow: Boolean) {
        mViewState.value = PrayerScheduleTodayVS.ShowLoader(isShow)
    }

    fun checkPrayerTime() {
        getPrayerScheduleTodayFromSession()?.apply {
            if (NetworkUtils.isConnected()) {
                getSelectedTime { year, month, day ->
                    if (requestDate == "$year-${month.adjustZero()}-${day.adjustZero()}") {
                        mViewState.value = PrayerScheduleTodayVS.ShowPrayerScheduleToday(this)
                    } else {
                        getPrayerScheduleToday(cityId = id, year = year, month = month, day = day) {
                            mViewState.value = PrayerScheduleTodayVS.ShowPrayerScheduleToday(this)
                        }
                    }
                }
            } else {
                mViewState.value = PrayerScheduleTodayVS.ShowPrayerScheduleToday(this)
            }
        }
    }

    private fun String.adjustZero(): String {
        return if ((this.toIntOrNull() ?: 0) < 10) {
            "0$this"
        } else {
            this
        }
    }

    fun onChangeCity(cityId: String, cityName: String) {
        getPrayerScheduleTodayFromSession()?.apply {
            id = cityId
            lokasi = cityName
            daerah = null
            jadwal = null
            requestDate = null

            getSelectedTime { year, month, day ->
                getPrayerScheduleToday(cityId = id, year = year, month = month, day = day)
            }
        }
    }

    private fun getPrayerScheduleTodayFromSession(): PrayerScheduleToday? {
        return TemanDoaSession.prayerScheduleToday.takeIf { it.isNotEmpty() }?.let {
            try {
                return Gson().fromJson(it, PrayerScheduleToday::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }
}