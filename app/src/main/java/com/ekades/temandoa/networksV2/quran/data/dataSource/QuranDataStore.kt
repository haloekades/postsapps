package com.ekades.temandoa.networksV2.quran.data.dataSource

import com.ekades.temandoa.features.qurandetail.model.SurahDetail
import com.ekades.temandoa.features.quranlist.model.Surah
import com.ekades.temandoa.lib.core.networkV2.BaseApiClient.Companion.BASE_URL_QURAN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuranDataStore {

    fun getAllQuranSurah(): Flow<List<Surah>> = flow {
        QuranApiClient.getApiClient(BASE_URL_QURAN).getAllQuranSurah().apply {
            emit(this)
        }
    }

    fun getQuranSurahDetail(number: String): Flow<List<SurahDetail>> = flow {
        QuranApiClient.getApiClient(BASE_URL_QURAN).getQuranSurahDetail(number).ayat.apply {
            emit(this)
        }
    }
}