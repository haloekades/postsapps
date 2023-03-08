package com.ekades.movieapps.features.main.state

import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleToday

sealed class PrayerScheduleTodayVS {
    class ShowPrayerScheduleToday(val prayerScheduleToday: PrayerScheduleToday) : PrayerScheduleTodayVS()
    class Error(val message: String?) : PrayerScheduleTodayVS()
    class ShowLoader(val showLoader: Boolean) : PrayerScheduleTodayVS()
}