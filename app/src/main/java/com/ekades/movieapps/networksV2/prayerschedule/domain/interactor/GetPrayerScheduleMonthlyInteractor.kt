package com.ekades.movieapps.networksV2.prayerschedule.domain.interactor

import com.ekades.movieapps.networksV2.prayerschedule.data.dataSource.response.PrayerScheduleMonthlyResponse
import com.ekades.movieapps.networksV2.prayerschedule.domain.repository.PrayerScheduleRepository
import com.ekades.movieapps.features.prayerschedule.prayerscheduledetail.model.*
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPrayerScheduleMonthlyInteractor(
    private val prayerScheduleRepository: PrayerScheduleRepository
) : Interactor<GetPrayerScheduleMonthlyInteractor.Params, Flow<PrayerScheduleMonthlyEntity>> {

    override fun execute(params: Params): Flow<PrayerScheduleMonthlyEntity> {
        return prayerScheduleRepository.getPrayerScheduleMonthly(
            cityId = params.cityId,
            year = params.year,
            month = params.month
        ).map {
            it.convertToEntity()
        }
    }

    private fun PrayerScheduleMonthlyResponse.convertToEntity(): PrayerScheduleMonthlyEntity {
        return PrayerScheduleMonthlyEntity(
            status = status,
            message = message,
            prayerScheduleDetail = convertToPrayerScheduleDetail()
        )
    }

    private fun PrayerScheduleMonthlyResponse.convertToPrayerScheduleDetail(): PrayerScheduleDetail {
        return PrayerScheduleDetail(
            id = data?.id.orEmpty(),
            lokasi = data?.lokasi.orEmpty(),
            daerah = data?.daerah.orEmpty(),
            dateSelectors = ArrayList(data?.jadwal?.convertToDateSelectors() ?: arrayListOf())
        )
    }

    private fun List<PrayerSchedule>.convertToDateSelectors(): List<DateSelector> {
        return this.map {
            val splitDate = it.date.split("-")
            val date = splitDate.getOrNull(2).orEmpty()
            val requestDate =
                splitDate.getOrNull(0).orEmpty() + "-" + splitDate.getOrNull(1).orEmpty()

            DateSelector(
                day = it.tanggal.split(",").getOrNull(0).orEmpty(),
                date = date,
                requestDate = requestDate,
                isSelected = false,
                shownPrayerTimeSchedule = ShownPrayerTimeSchedule(it)
            )
        }
    }

    data class Params(
        val cityId: String,
        val year: String,
        val month: String
    )
}