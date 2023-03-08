package com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model

data class PrayerScheduleMonthlyEntity(
    val status: Boolean,
    val prayerScheduleDetail: PrayerScheduleDetail? = null,
    val message: String? = null
)