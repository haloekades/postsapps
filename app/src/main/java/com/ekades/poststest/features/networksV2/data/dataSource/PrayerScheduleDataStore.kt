package com.ekades.poststest.features.networksV2.data.dataSource

import com.ekades.poststest.features.networksV2.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.poststest.features.prayerschedule.searchcity.model.CityItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PrayerScheduleDataStore {

    fun getUsers(): Flow<List<CityItem>> = flow {
        PrayerScheduleApiClient.getApiClient().getAllCity().apply {
            emit(this)
        }
    }

    fun getPrayerScheduleToday(
        cityId: String, year: String, month: String, day: String
    ): Flow<PrayerCityScheduleResponse> = flow {
        emit(
            PrayerScheduleApiClient.getApiClient().getPrayerScheduleToday(
                cityId, year, month, day
            )
        )
    }

}