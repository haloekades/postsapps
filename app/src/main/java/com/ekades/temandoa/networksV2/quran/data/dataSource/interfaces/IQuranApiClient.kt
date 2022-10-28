package com.ekades.temandoa.networksV2.quran.data.dataSource.interfaces

import com.ekades.temandoa.features.qurandetail.model.SurahDetail
import com.ekades.temandoa.features.quranlist.model.Surah
import retrofit2.http.GET
import retrofit2.http.Path

interface IQuranApiClient {

    @GET("data")
    suspend fun getAllQuranSurah(): List<Surah>

    @GET("surat/{number}")
    suspend fun getQuranSurahDetail(
        @Path("number") number: String
    ): List<SurahDetail>
}