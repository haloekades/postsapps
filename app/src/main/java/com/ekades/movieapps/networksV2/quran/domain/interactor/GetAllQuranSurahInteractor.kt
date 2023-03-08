package com.ekades.movieapps.networksV2.quran.domain.interactor

import com.ekades.movieapps.networksV2.quran.domain.repository.QuranRepository
import com.ekades.movieapps.features.quranlist.model.Surah
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetAllQuranSurahInteractor(
    private val quranRepository: QuranRepository
) : Interactor<Interactor.None, Flow<List<Surah>>> {

    override fun execute(params: Interactor.None): Flow<List<Surah>> {
        return quranRepository.getAllQuranSurah()
    }
}