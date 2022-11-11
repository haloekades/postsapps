package com.ekades.ruangmuslim.networksV2.quran.data.dataSource.response

import com.ekades.ruangmuslim.features.qurandetail.model.SurahDetail

data class QuranSurahDetailResponse(
    val ayat: List<SurahDetail> = arrayListOf()
)