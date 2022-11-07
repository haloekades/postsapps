package com.ekades.temandoa.features.main.model

data class MainSection(
    val id: Int,
    val name: String,
    var icon: Int? = null,
    val jsonFile: String? = null,
    val isShowDetail: Boolean
) {
    companion object {
        const val ID_QURAN = 0
        const val ID_MUROTTAL = 1
    }
}