package com.ekades.temandoa.features.networksV2.domain.repository

import com.ekades.temandoa.features.networksV2.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.temandoa.features.networksV2.data.dataSource.response.PrayerScheduleMonthlyResponse
import com.ekades.temandoa.features.prayerschedule.searchcity.model.CityItem
import kotlinx.coroutines.flow.Flow

interface PrayerScheduleRepository {
    fun getAllCity(): Flow<List<CityItem>>

    fun getPrayerScheduleToday(
        cityId: String,
        year: String,
        month: String,
        day: String
    ): Flow<PrayerCityScheduleResponse>

    fun getPrayerScheduleMonthly(
        cityId: String,
        year: String,
        month: String
    ): Flow<PrayerScheduleMonthlyResponse>
}