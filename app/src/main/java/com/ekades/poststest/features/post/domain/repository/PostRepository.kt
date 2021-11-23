package com.ekades.poststest.features.post.domain.repository

import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<PostEntity>>
    fun getPostById(id: String): Flow<PostEntity>
}