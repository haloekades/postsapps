package com.ekades.poststest.features.main.model

class MainSection(
    val id: Int,
    val name: String,
    var icon: Int? = null,
    val jsonFile: String? = null,
    val isShowDetail: Boolean
)