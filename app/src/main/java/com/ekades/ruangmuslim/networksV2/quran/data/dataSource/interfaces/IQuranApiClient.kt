package com.ekades.ruangmuslim.networksV2.quran.data.dataSource.interfaces

import com.ekades.ruangmuslim.features.quranlist.model.Surah
import com.ekades.ruangmuslim.networksV2.quran.data.dataSource.response.QuranSurahDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IQuranApiClient {

    @GET("surat")
    suspend fun getAllQuranSurah(): List<Surah>

    @GET("surat/{number}")
    suspend fun getQuranSurahDetail(
        @Path("number") number: String
    ): QuranSurahDetailResponse
}