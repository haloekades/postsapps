package com.ekades.poststest.features.users.presentation.model

data class User(
    val id: Int?,
    val login: String?,
    val avatarUrl: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val email: String? = null
)