package com.ekades.poststest.features.networksV2.domain.repository

import com.ekades.poststest.features.networksV2.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.poststest.features.prayerschedule.searchcity.model.CityItem
import kotlinx.coroutines.flow.Flow

interface PrayerScheduleRepository {
    fun getAllCity(): Flow<List<CityItem>>

    fun getPrayerScheduleToday(
        cityId: String,
        year: String,
        month: String,
        day: String
    ): Flow<PrayerCityScheduleResponse>
}