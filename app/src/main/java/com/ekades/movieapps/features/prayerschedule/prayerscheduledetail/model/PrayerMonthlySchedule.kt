package com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model

data class PrayerMonthlySchedule(
    val id: String,
    val lokasi: String,
    val daerah: String,
    val jadwal: List<PrayerSchedule>
)
