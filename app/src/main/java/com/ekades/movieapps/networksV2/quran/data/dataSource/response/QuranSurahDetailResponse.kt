package com.ekades.movieapps.networksV2.quran.data.dataSource.response

import com.ekades.movieapps.features.qurandetail.model.SurahDetail

data class QuranSurahDetailResponse(
    val ayat: List<SurahDetail> = arrayListOf()
)