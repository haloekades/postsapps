package com.ekades.movieapps.networksV2.prayerschedule.domain.interactor

import com.ekades.movieapps.networksV2.prayerschedule.domain.repository.PrayerScheduleRepository
import com.ekades.movieapps.features.prayerschedule.searchcity.model.CityItem
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetAllCityInteractor(
    private val prayerScheduleRepository: PrayerScheduleRepository
) : Interactor<Interactor.None, Flow<List<CityItem>>> {

    override fun execute(params: Interactor.None): Flow<List<CityItem>> {
        return prayerScheduleRepository.getAllCity()
    }
}