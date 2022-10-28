package com.ekades.temandoa.networksV2.prayerschedule.data.dataSource.response

import com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.model.PrayerMonthlySchedule

data class PrayerScheduleMonthlyResponse(
    val status: Boolean,
    val data: PrayerMonthlySchedule? = null,
    val message: String? = null
)