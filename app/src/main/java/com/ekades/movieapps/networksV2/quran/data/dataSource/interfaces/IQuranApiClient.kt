package com.ekades.movieapps.networksV2.quran.data.dataSource.interfaces

import com.ekades.movieapps.features.quranlist.model.Surah
import com.ekades.movieapps.networksV2.quran.data.dataSource.response.QuranSurahDetailResponse
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