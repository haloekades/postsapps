package com.ekades.poststest.features.users.domain.interactor

import com.ekades.poststest.features.users.domain.repository.UserRepository
import com.ekades.poststest.features.users.domain.model.UserEntity
import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetUsersInteractor(
    private val postRepository: UserRepository
) : Interactor<Interactor.None, Flow<List<UserEntity>>> {

    override fun execute(params: Interactor.None): Flow<List<UserEntity>> {
        return postRepository.getUsers()
    }
}