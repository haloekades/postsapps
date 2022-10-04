package com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.state

import com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.model.DateSelector
import com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.model.ScheduleItem

sealed class PrayerScheduleMonthlyVS {
    class Success(
        val city: String,
        val month: String,
        val dateSelector: ArrayList<DateSelector>,
        val scheduleItemList: List<ScheduleItem>,
        val scrollToIndex: Int? = null
    ) : PrayerScheduleMonthlyVS()

    class Error(val message: String?) : PrayerScheduleMonthlyVS()
    class ShowLoader(val showLoader: Boolean) : PrayerScheduleMonthlyVS()
}