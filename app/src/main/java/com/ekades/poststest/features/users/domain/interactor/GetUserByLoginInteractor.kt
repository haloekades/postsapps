package com.carlosgub.coroutines.features.books.domain.interactor

import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import com.ekades.poststest.features.users.domain.repository.UserRepository
import com.ekades.poststest.features.users.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow

class GetUserByLoginInteractor(
    private val postRepository: UserRepository
) : Interactor<GetUserByLoginInteractor.Params, Flow<UserEntity>> {

    override fun execute(params: Params): Flow<UserEntity> {
        return postRepository.getUserByLogin(login = params.login)
    }

    data class Params(val login: String)
}