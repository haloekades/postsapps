package com.ekades.poststest.features.post.data.dataSource

import com.carlosgub.coroutines.features.books.data.datasource.rest.PostApiClient
import com.ekades.poststest.features.post.data.dataSource.response.PostResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRestDataStore {

    fun getPosts(): Flow<List<PostResponseData>> = flow {
        PostApiClient.getApiClient().getPosts().apply {
            emit(this)
        }
    }

    fun getPostById(id: String): Flow<PostResponseData> = flow {
        emit(PostApiClient.getApiClient().getPostById(id = id))
    }

}