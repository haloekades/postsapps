package com.ekades.ruangmuslim.features.main.state

import com.ekades.ruangmuslim.features.prayerschedule.prayerscheduledetail.model.PrayerScheduleToday

sealed class PrayerScheduleTodayVS {
    class ShowPrayerScheduleToday(val prayerScheduleToday: PrayerScheduleToday) : PrayerScheduleTodayVS()
    class Error(val message: String?) : PrayerScheduleTodayVS()
    class ShowLoader(val showLoader: Boolean) : PrayerScheduleTodayVS()
}