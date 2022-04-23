package com.ekades.poststest.features.users.data.dataSource.response

data class UserResponseData(
    val id: Int?,
    val login: String?,
    val avatarUrl: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val email: String? = null
)