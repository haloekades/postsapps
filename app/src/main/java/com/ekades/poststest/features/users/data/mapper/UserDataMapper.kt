package com.ekades.poststest.features.users.data.mapper

import com.ekades.poststest.lib.core.networkV2.mapper.Mapper
import com.ekades.poststest.features.users.data.dataSource.response.UserResponseData
import com.ekades.poststest.features.users.domain.model.UserEntity

class UserDataMapper : Mapper<UserResponseData, UserEntity> {
    override fun map(origin: UserResponseData) =
        UserEntity(
            id = origin.id,
            login = origin.login,
            avatarUrl = origin.avatarUrl,
            name = origin.name,
            company = origin.company,
            location = origin.location,
            email = origin.email
        )
}