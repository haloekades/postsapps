package com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.model

data class PrayerScheduleDetail(
    val id: String,
    val lokasi: String,
    val daerah: String,
    var dateSelectors: ArrayList<DateSelector>
)
