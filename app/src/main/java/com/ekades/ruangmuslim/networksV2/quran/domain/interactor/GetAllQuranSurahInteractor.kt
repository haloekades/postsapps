package com.ekades.ruangmuslim.networksV2.quran.domain.interactor

import com.ekades.ruangmuslim.networksV2.quran.domain.repository.QuranRepository
import com.ekades.ruangmuslim.features.quranlist.model.Surah
import com.ekades.ruangmuslim.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetAllQuranSurahInteractor(
    private val quranRepository: QuranRepository
) : Interactor<Interactor.None, Flow<List<Surah>>> {

    override fun execute(params: Interactor.None): Flow<List<Surah>> {
        return quranRepository.getAllQuranSurah()
    }
}