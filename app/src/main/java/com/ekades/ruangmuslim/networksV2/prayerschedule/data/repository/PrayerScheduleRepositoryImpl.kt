package com.ekades.ruangmuslim.networksV2.prayerschedule.data.repository

import com.ekades.ruangmuslim.networksV2.prayerschedule.data.dataSource.PrayerScheduleDataStore
import com.ekades.ruangmuslim.networksV2.prayerschedule.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.ruangmuslim.networksV2.prayerschedule.data.dataSource.response.PrayerScheduleMonthlyResponse
import com.ekades.ruangmuslim.networksV2.prayerschedule.domain.repository.PrayerScheduleRepository
import com.ekades.ruangmuslim.features.prayerschedule.searchcity.model.CityItem
import kotlinx.coroutines.flow.Flow

class PrayerScheduleRepositoryImpl(
    private val prayerScheduleDataStore: PrayerScheduleDataStore
) : PrayerScheduleRepository {

    override fun getAllCity(): Flow<List<CityItem>> = prayerScheduleDataStore.getUsers()

    override fun getPrayerScheduleToday(
        cityId: String,
        year: String,
        month: String,
        day: String
    ): Flow<PrayerCityScheduleResponse> =
        prayerScheduleDataStore.getPrayerScheduleToday(cityId, year, month, day)

    override fun getPrayerScheduleMonthly(
        cityId: String,
        year: String,
        month: String
    ): Flow<PrayerScheduleMonthlyResponse> =
        prayerScheduleDataStore.getPrayerScheduleMonthly(cityId, year, month)
}
