package com.ekades.movieapps.networksV2.quran.data.repository

import com.ekades.movieapps.features.qurandetail.model.SurahDetail
import com.ekades.movieapps.networksV2.quran.data.dataSource.QuranDataStore
import com.ekades.movieapps.networksV2.quran.domain.repository.QuranRepository
import com.ekades.movieapps.features.quranlist.model.Surah
import kotlinx.coroutines.flow.Flow

class QuranRepositoryImpl(
    private val quranDataStore: QuranDataStore
) : QuranRepository {

    override fun getAllQuranSurah(): Flow<List<Surah>> = quranDataStore.getAllQuranSurah()

    override fun getQuranSurahDetail(number: String): Flow<List<SurahDetail>> = quranDataStore.getQuranSurahDetail(number)
}