package com.ekades.poststest.features.post.domain.interactor

import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.ekades.poststest.features.post.domain.repository.PostRepository
import com.ekades.poststest.lib.core.networkV2.interactor.Interactor
import kotlinx.coroutines.flow.Flow

class GetPostsInteractor(
    private val postRepository: PostRepository
) : Interactor<Interactor.None, Flow<List<PostEntity>>> {

    override fun execute(params: Interactor.None): Flow<List<PostEntity>> {
        return postRepository.getPosts()
    }
}