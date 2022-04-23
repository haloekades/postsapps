package com.ekades.poststest.features.users.data.repository

import com.ekades.poststest.features.users.data.dataSource.UserRestDataStore
import com.ekades.poststest.features.users.data.mapper.UserDataMapper
import com.ekades.poststest.features.users.domain.repository.UserRepository
import com.ekades.poststest.features.users.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userRestDataStore: UserRestDataStore
) : UserRepository {

    private val mUserDataMapper by lazy { UserDataMapper() }

    override fun getUsers(): Flow<List<UserEntity>> =
        userRestDataStore.getUsers().map {
            mUserDataMapper.map(it)
        }

    override fun getUserByLogin(login: String): Flow<UserEntity> =
        userRestDataStore.getUserByLogin(login = login).map {
            mUserDataMapper.map(it)
        }
}
