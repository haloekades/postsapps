package com.ekades.poststest.features.networksV2.domain.interactor

import com.ekades.poststest.features.networksV2.data.dataSource.response.PrayerCityScheduleResponse
import com.ekades.poststest.features.networksV2.domain.repository.PrayerScheduleRepository
import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetPrayerScheduleTodayInteractor(
    private val prayerScheduleRepository: PrayerScheduleRepository
) : Interactor<GetPrayerScheduleTodayInteractor.Params, Flow<PrayerCityScheduleResponse>> {

    override fun execute(params: Params): Flow<PrayerCityScheduleResponse> {
        return prayerScheduleRepository.getPrayerScheduleToday(
            cityId = params.cityId,
            year = params.year,
            month = params.month,
            day = params.day
        )
    }

    data class Params(
        val cityId: String,
        val year: String,
        val month: String,
        val day: String
    )
}