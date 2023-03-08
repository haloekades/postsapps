package com.ekades.movieapps.networksV2.quran.domain.interactor

import com.ekades.movieapps.features.qurandetail.model.SurahDetail
import com.ekades.movieapps.networksV2.quran.domain.repository.QuranRepository
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
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