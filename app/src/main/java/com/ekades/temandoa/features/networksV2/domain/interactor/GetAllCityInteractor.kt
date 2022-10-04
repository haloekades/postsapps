package com.ekades.temandoa.features.networksV2.domain.interactor

import com.ekades.temandoa.features.networksV2.domain.repository.PrayerScheduleRepository
import com.ekades.temandoa.features.prayerschedule.searchcity.model.CityItem
import com.ekades.temandoa.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetAllCityInteractor(
    private val prayerScheduleRepository: PrayerScheduleRepository
) : Interactor<Interactor.None, Flow<List<CityItem>>> {

    override fun execute(params: Interactor.None): Flow<List<CityItem>> {
        return prayerScheduleRepository.getAllCity()
    }
}