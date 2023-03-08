package com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model

data class DateSelector(
    val day: String,
    val date: String,
    var requestDate: String,
    var isSelected: Boolean,
    var shownPrayerTimeSchedule: ShownPrayerTimeSchedule
)
