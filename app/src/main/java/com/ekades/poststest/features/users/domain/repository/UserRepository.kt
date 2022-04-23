package com.ekades.poststest.features.users.domain.repository

import com.ekades.poststest.features.users.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<UserEntity>>
    fun getUserByLogin(login: String): Flow<UserEntity>
}