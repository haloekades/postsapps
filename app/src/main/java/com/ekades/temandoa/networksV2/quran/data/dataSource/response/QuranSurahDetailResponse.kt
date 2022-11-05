package com.ekades.temandoa.networksV2.quran.data.dataSource.response

import com.ekades.temandoa.features.qurandetail.model.SurahDetail

data class QuranSurahDetailResponse(
    val ayat: List<SurahDetail> = arrayListOf()
)