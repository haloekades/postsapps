package com.ekades.ruangmuslim.networksV2.quran.domain.repository

import com.ekades.ruangmuslim.features.qurandetail.model.SurahDetail
import com.ekades.ruangmuslim.features.quranlist.model.Surah
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getAllQuranSurah(): Flow<List<Surah>>

    fun getQuranSurahDetail(number: String): Flow<List<SurahDetail>>
}