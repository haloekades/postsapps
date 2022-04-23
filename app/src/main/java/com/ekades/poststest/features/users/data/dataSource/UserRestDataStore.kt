package com.ekades.poststest.features.users.data.dataSource

import com.ekades.poststest.features.users.data.dataSource.response.UserResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRestDataStore {

    fun getUsers(): Flow<List<UserResponseData>> = flow {
        UserApiClient.getApiClient().getUsers().apply {
            emit(this)
        }
    }

    fun getUserByLogin(login: String): Flow<UserResponseData> = flow {
        emit(UserApiClient.getApiClient().getUserByLogin(login = login))
    }

}