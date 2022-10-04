package com.ekades.temandoa.features.networksV2.data.dataSource.response

import com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.model.PrayerMonthlySchedule

data class PrayerScheduleMonthlyResponse(
    val status: Boolean,
    val data: PrayerMonthlySchedule? = null,
    val message: String? = null
)