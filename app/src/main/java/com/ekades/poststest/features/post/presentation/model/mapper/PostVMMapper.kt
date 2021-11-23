package com.ekades.poststest.features.post.presentation.model.mapper

import com.carlosgub.coroutines.features.books.domain.model.PostEntity
import com.ekades.poststest.features.post.presentation.model.PostVM
import com.ekades.poststest.lib.core.networkV2.mapper.Mapper
import com.ekades.poststest.models.Post

class PostVMMapper : Mapper<PostEntity, Post> {
    override fun map(origin: PostEntity) =
        Post(
            userId = origin.userId,
            id = origin.id,
            title = origin.title,
            body = origin.body
        )
}