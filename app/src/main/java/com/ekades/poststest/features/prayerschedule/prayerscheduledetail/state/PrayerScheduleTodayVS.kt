package com.ekades.poststest.features.prayerschedule.prayerscheduledetail.state

import com.ekades.poststest.features.prayerschedule.prayerscheduledetail.model.PrayerCitySchedule

sealed class PrayerScheduleTodayVS {
    class Success(val prayerCitySchedule: PrayerCitySchedule): PrayerScheduleTodayVS()
    class Error(val message:String?): PrayerScheduleTodayVS()
    class ShowLoader(val showLoader:Boolean): PrayerScheduleTodayVS()
}