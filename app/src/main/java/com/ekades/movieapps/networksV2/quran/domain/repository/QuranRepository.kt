package com.ekades.movieapps.networksV2.quran.domain.repository

import com.ekades.movieapps.features.qurandetail.model.SurahDetail
import com.ekades.movieapps.features.quranlist.model.Surah
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getAllQuranSurah(): Flow<List<Surah>>

    fun getQuranSurahDetail(number: String): Flow<List<SurahDetail>>
}