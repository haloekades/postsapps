package com.ekades.movieapps.networksV2.prayerschedule.domain.repository

import com.ekades.movieapps.networksV2.prayerschedule.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.movieapps.networksV2.prayerschedule.data.dataSource.response.PrayerScheduleMonthlyResponse
import com.ekades.movieapps.features.prayerschedule.searchcity.model.CityItem
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