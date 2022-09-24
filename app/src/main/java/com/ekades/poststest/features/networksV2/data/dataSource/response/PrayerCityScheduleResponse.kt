package com.ekades.poststest.features.networksV2.data.dataSource.response

import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.model.PrayerCitySchedule

data class PrayerCityScheduleResponse(
    val status: Boolean,
    val data: PrayerCitySchedule? = null,
    val message: String? = null
)