package com.ekades.poststest.features.post.presentation.viewModels.state

import com.ekades.poststest.features.post.presentation.model.PostVM

sealed class PostVS {
    class Post(val postVM: PostVM): PostVS()
    class Error(val message:String?): PostVS()
    class ShowLoader(val showLoader:Boolean): PostVS()
}