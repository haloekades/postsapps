package com.ekades.temandoa.networksV2.quran.domain.interactor

import com.ekades.temandoa.features.qurandetail.model.SurahDetail
import com.ekades.temandoa.networksV2.quran.domain.repository.QuranRepository
import com.ekades.temandoa.lib.core.networkV2.interactor.Interactor
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