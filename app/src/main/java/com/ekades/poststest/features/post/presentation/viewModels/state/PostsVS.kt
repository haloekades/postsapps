package com.ekades.poststest.features.post.presentation.viewModels.state

import com.ekades.poststest.models.Post

sealed class PostsVS {
    class AddPost(val postsVM: List<Post>): PostsVS()
    class Error(val message:String?): PostsVS()
    class ShowLoader(val showLoader:Boolean): PostsVS()
}