package com.ekades.temandoa.features.prayerdetail.model

data class PrayerItem(
    val id: Int,
    val name: String,
    val arabicHeader: String?,
    val arabicHeaderMeaning: String?,
    var arabic: String,
    val latin: String,
    val arabicMeaning: String,
    val readCount: Int?,
    val benefits: String?,
    val tafsir: String?,
    val source: String?
)