package com.ekades.temandoa.networksV2.prayerschedule.data.dataSource

import com.ekades.temandoa.networksV2.prayerschedule.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.temandoa.networksV2.prayerschedule.data.dataSource.response.PrayerScheduleMonthlyResponse
import com.ekades.temandoa.features.prayerschedule.searchcity.model.CityItem
import com.ekades.temandoa.lib.core.networkV2.BaseApiClient.Companion.BASE_URL_PRAYER_SCHEDULE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PrayerScheduleDataStore {

    fun getUsers(): Flow<List<CityItem>> = flow {
        PrayerScheduleApiClient.getApiClient(BASE_URL_PRAYER_SCHEDULE).getAllCity().apply {
            emit(this)
        }
    }

    fun getPrayerScheduleToday(
        cityId: String, year: String, month: String, day: String
    ): Flow<PrayerCityScheduleResponse> = flow {
        emit(
            PrayerScheduleApiClient.getApiClient(BASE_URL_PRAYER_SCHEDULE).getPrayerScheduleToday(
                cityId, year, month, day
            )
        )
    }

    fun getPrayerScheduleMonthly(
        cityId: String, year: String, month: String
    ): Flow<PrayerScheduleMonthlyResponse> = flow {
        emit(
            PrayerScheduleApiClient.getApiClient(BASE_URL_PRAYER_SCHEDULE).getPrayerScheduleMonthly(
                cityId, year, month
            )
        )
    }

}