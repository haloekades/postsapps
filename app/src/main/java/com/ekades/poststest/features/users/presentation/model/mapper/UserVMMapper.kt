package com.ekades.poststest.features.users.presentation.model.mapper

import com.ekades.poststest.features.users.domain.model.UserEntity
import com.ekades.poststest.features.users.presentation.model.User
import com.ekades.poststest.lib.core.networkV2.mapper.Mapper

class UserVMMapper : Mapper<UserEntity, User> {
    override fun map(origin: UserEntity) =
        User(
            id = origin.id,
            login = origin.login,
            avatarUrl = origin.avatarUrl,
            name = origin.name,
            company = origin.company,
            location = origin.location,
            email = origin.email
        )
}