package com.ekades.poststest.features.post.data.mapper

import com.ekades.poststest.lib.core.networkV2.mapper.Mapper
import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.ekades.poststest.features.post.data.dataSource.response.PostResponseData

class PostDataMapper : Mapper<PostResponseData, PostEntity> {
    override fun map(origin: PostResponseData) =
        PostEntity(
            userId = origin.userId,
            id = origin.id,
            title = origin.title,
            body = origin.body
        )
}