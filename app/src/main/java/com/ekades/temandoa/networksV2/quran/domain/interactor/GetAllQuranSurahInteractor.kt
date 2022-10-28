package com.ekades.temandoa.networksV2.quran.domain.interactor

import com.ekades.temandoa.networksV2.quran.domain.repository.QuranRepository
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetAllQuranSurahInteractor(
    private val quranRepository: QuranRepository
) : Interactor<Interactor.None, Flow<List<Surah>>> {

    override fun execute(params: Interactor.None): Flow<List<Surah>> {
        return quranRepository.getAllQuranSurah()
    }
}