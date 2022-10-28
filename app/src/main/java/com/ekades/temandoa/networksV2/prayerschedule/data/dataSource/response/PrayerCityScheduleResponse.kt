package com.ekades.temandoa.networksV2.prayerschedule.data.dataSource.response

import com.ekades.temandoa.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleToday

data class PrayerCityScheduleResponse(
    val status: Boolean,
    val data: PrayerScheduleToday? = null,
    val message: String? = null
)