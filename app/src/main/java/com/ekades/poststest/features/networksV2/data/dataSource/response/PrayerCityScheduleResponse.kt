package com.ekades.poststest.features.networksV2.data.dataSource.response

import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleToday

data class PrayerCityScheduleResponse(
    val status: Boolean,
    val data: PrayerScheduleToday? = null,
    val message: String? = null
)