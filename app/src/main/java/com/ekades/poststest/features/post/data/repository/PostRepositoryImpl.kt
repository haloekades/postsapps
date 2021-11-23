package com.ekades.poststest.features.post.data.repository

import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.ekades.poststest.features.post.domain.repository.PostRepository
import com.ekades.poststest.features.post.data.dataSource.PostRestDataStore
import com.ekades.poststest.features.post.data.mapper.PostDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val postRestDataStore: PostRestDataStore
) : PostRepository {

    private val mPostDataMapper by lazy { PostDataMapper() }

    override fun getPosts(): Flow<List<PostEntity>> =
        postRestDataStore.getPosts().map {
            mPostDataMapper.map(it)
        }

    override fun getPostById(id: String): Flow<PostEntity> =
        postRestDataStore.getPostById(id = id).map {
            mPostDataMapper.map(it)
        }
}
