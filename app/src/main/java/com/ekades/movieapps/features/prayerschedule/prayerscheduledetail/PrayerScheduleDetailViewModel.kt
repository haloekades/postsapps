package com.ekades.movieapps.features.prayerschedule.prayerscheduledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekades.movieapps.networksV2.prayerschedule.domain.interactor.GetPrayerScheduleMonthlyInteractor
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model.DateSelector
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleDetail
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleToday
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.state.PrayerScheduleMonthlyVS
import com.ekades.movieapps.lib.application.preferences.TemanDoaSession
import com.ekades.movieapps.lib.core.networkV2.utils.io
import com.ekades.movieapps.lib.core.networkV2.utils.ui
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class PrayerScheduleDetailViewModel(
    private val getPrayerScheduleMonthlyInteractor: GetPrayerScheduleMonthlyInteractor
) : ViewModel() {

    val viewState: LiveData<PrayerScheduleMonthlyVS> get() = mViewState
    private val mViewState = MutableLiveData<PrayerScheduleMonthlyVS>()

    private var mPrayerScheduleDetail: PrayerScheduleDetail? = null

    var currentMonth = ""
    var mMonth = ""
    var addMonth = 0

    fun setCurrentMonth() {
        try {
            addMonth = 0
            val dt = Date()
            val c = Calendar.getInstance()
            c.time = dt
            currentMonth =
                c.get(Calendar.YEAR).toString() + "-" + (c.get(Calendar.MONTH) + 1).toString()
                    .adjustZero()
        } catch (e: Exception) {
        }
    }

    fun clearScheduleDetail() {
        mPrayerScheduleDetail = null
    }

    private fun getSelectedTime(
        result: (year: String, month: String, day: String) -> Unit
    ) = viewModelScope.launch {
        try {
            val dt = Date()
            val c = Calendar.getInstance()
            c.time = dt
            c.add(Calendar.MONTH, addMonth)
            result.invoke(
                c.get(Calendar.YEAR).toString(),
                (c.get(Calendar.MONTH) + 1).toString(),
                c.get(Calendar.DATE).toString()
            )
        } catch (e: Exception) {
        }
    }

    fun onAfterDay(cityId: String) {
        addMonth++
        getPrayerScheduleMonthly(cityId)
    }

    fun onBeforeDay(cityId: String) {
        addMonth--
        getPrayerScheduleMonthly(cityId)
    }

    private fun String.adjustZero(): String {
        return if ((this.toIntOrNull() ?: 0) < 10) {
            "0$this"
        } else {
            this
        }
    }

    fun getPrayerScheduleMonthly(cityId: String) {
        getSelectedTime { year, month, _ ->
            mMonth = "$year-${month.adjustZero()}"
            val dateSelectors = getDateSelectors()

            dateSelectors?.takeIf { it.isNotEmpty() }?.apply {
                updateSelectedDate(isNeedToScrollPosition = true)
            } ?: apply {
                getPrayerScheduleMonthly(cityId, year, month)
            }
        }
    }

    private fun List<DateSelector>.getDefaultSelectedDate(): String {
        return if (addMonth < 0) {
            last().date
        } else if (addMonth == 0) {
            today()
        } else {
            first().date
        }
    }

    private fun getPrayerScheduleMonthly(cityId: String, year: String, month: String) {
        viewModelScope.launch {
            try {
                showLoader(true)
                io {
                    val params = GetPrayerScheduleMonthlyInteractor.Params(
                        cityId, year, month
                    )
                    getPrayerScheduleMonthlyInteractor.execute(params = params)
                        .collect {
                            io {
                                if (it.status) {
                                    if (mPrayerScheduleDetail == null) {
                                        mPrayerScheduleDetail = it.prayerScheduleDetail
                                    } else if (it.prayerScheduleDetail?.dateSelectors?.isNotEmpty() == true) {
                                        mPrayerScheduleDetail?.apply {
                                            dateSelectors.addAll(it.prayerScheduleDetail.dateSelectors)
                                        }
                                    }

                                    updateSelectedDate(isNeedToScrollPosition = true)
                                } else {
                                    ui {
                                        onFailed(it.message.orEmpty())
                                    }
                                }

                            }
                        }
                }
            } catch (e: Exception) {
                onFailed(e.message.orEmpty())
            }
        }
    }

    private fun savePrayerScheduleToday(prayerScheduleToday: PrayerScheduleToday) {
        TemanDoaSession.prayerScheduleToday = prayerScheduleToday.toJson()
    }

    private fun getDateSelectors(): List<DateSelector>? {
        return mPrayerScheduleDetail?.dateSelectors?.filter {
            it.requestDate.contains(mMonth)
        }
    }

    fun updateSelectedDate(selectedDate: String? = null, isNeedToScrollPosition: Boolean = false) =
        viewModelScope.launch {
            val dateSelectors = getDateSelectors()
            var scrollToIndex: Int? = null

            dateSelectors?.apply {
                val defaultSelectedDate = if (selectedDate?.isNotBlank() == true) {
                    selectedDate
                } else {
                    getDefaultSelectedDate()
                }


                forEachIndexed { index, item ->
                    item.isSelected = item.date == defaultSelectedDate

                    if (isNeedToScrollPosition && item.isSelected) {
                        scrollToIndex = index

                        if (addMonth == 0) {
                            mPrayerScheduleDetail?.apply {
                                savePrayerScheduleToday(
                                    PrayerScheduleToday(
                                        id = id,
                                        lokasi = lokasi,
                                        daerah = daerah,
                                        jadwal = item.shownPrayerTimeSchedule.jadwal,
                                        requestDate = item.requestDate + "-" + item.date
                                    )
                                )
                            }
                        }
                    }
                }
            }

            ui {
                onSuccess(
                    dateSelectors = ArrayList(
                        dateSelectors ?: arrayListOf()
                    ),
                    scrollToIndex = scrollToIndex
                )
            }
        }

    private fun today(): String {
        val dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        return c.get(Calendar.DATE).toString().adjustZero()
    }

    private fun onSuccess(dateSelectors: ArrayList<DateSelector>, scrollToIndex: Int? = null) {
        mViewState.value = PrayerScheduleMonthlyVS.Success(
            city = mPrayerScheduleDetail?.lokasi.orEmpty(),
            month = getShownMonth(),
            dateSelector = dateSelectors,
            scheduleItemList = dateSelectors.find { it.isSelected }?.shownPrayerTimeSchedule?.getScheduleItems()
                ?: arrayListOf(),
            scrollToIndex = scrollToIndex
        )
        showLoader(false)
    }

    private fun getShownMonth(): String {
        val monthSplit = mMonth.split("-")
        return monthSplit.let {
            it.getOrNull(1)?.convertToIndo() + " " + it.getOrNull(0)
        }
    }

    private fun String.convertToIndo(): String {
        return when (this) {
            "01" -> "Januari"
            "02" -> "Februari"
            "03" -> "Maret"
            "04" -> "April"
            "05" -> "Mei"
            "06" -> "Juni"
            "07" -> "Juli"
            "08" -> "Agustus"
            "09" -> "September"
            "10" -> "Oktober"
            "11" -> "November"
            "12" -> "Desember"
            else -> ""
        }
    }

    private fun onFailed(errorMessage: String) {
        mViewState.value = PrayerScheduleMonthlyVS.Error(errorMessage)
        showLoader(false)
    }

    private fun showLoader(isShow: Boolean) {
        mViewState.value = PrayerScheduleMonthlyVS.ShowLoader(isShow)
    }
}