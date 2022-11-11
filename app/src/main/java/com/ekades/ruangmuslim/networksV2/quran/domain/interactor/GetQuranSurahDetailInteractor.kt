package com.ekades.ruangmuslim.networksV2.quran.domain.interactor

import com.ekades.ruangmuslim.features.qurandetail.model.SurahDetail
import com.ekades.ruangmuslim.networksV2.quran.domain.repository.QuranRepository
import com.ekades.ruangmuslim.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetQuranSurahDetailInteractor(
    private val quranRepository: QuranRepository
) : Interactor<GetQuranSurahDetailInteractor.Params, Flow<List<SurahDetail>>> {

    override fun execute(params: Params): Flow<List<SurahDetail>> {
        return quranRepository.getQuranSurahDetail(params.number)
    }

    data class Params(
        val number: String
    )
}