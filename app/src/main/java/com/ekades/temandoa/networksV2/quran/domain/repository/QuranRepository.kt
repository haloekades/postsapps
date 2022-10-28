package com.ekades.temandoa.networksV2.quran.domain.repository

import com.ekades.temandoa.features.qurandetail.model.SurahDetail
import com.ekades.temandoa.features.quranlist.model.Surah
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getAllQuranSurah(): Flow<List<Surah>>

    fun getQuranSurahDetail(number: String): Flow<List<SurahDetail>>
}