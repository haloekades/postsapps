package com.ekades.poststest.test.utils

import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object TestData {
    val dataEmpty = emptyList<PostEntity>()

    val postList = listOf(
        PostEntity(1, 1, "Harry Potter 1", "First book"),
        PostEntity(2, 2, "Harry Potter 2", "Second book"),
        PostEntity(3, 3, "Harry Potter 3", "Third book")
    )
    val dataList: Flow<List<PostEntity>> = flow {
        postList.apply {
            emit(this)
        }
    }
}