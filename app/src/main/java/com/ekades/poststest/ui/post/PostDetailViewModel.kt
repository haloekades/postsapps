package com.ekades.poststest.ui.post

import android.content.Intent
import androidx.annotation.VisibleForTesting
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.poststest.networks.dataSource.MainDataSource
import com.ekades.poststest.lib.core.network.responses.ApiResponse
import com.ekades.poststest.lib.core.network.responses.constant.StatusApiResponse
import com.ekades.poststest.models.Comment
import com.ekades.poststest.models.Post
import com.ekades.poststest.models.User
import com.ekades.poststest.ui.post.PostDetailActivity.Companion.EXTRA_POST
import com.ekades.poststest.ui.post.PostDetailActivity.Companion.EXTRA_USER

class PostDetailViewModel : BaseViewModel() {

    var getCommentsApiResponse = mutableLiveDataOf<ApiResponse>()
    val comments = mutableLiveDataOf<ArrayList<Comment>>()

    var post: Post? = null
    var user: User? = null

    fun processIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_POST)) {
            post = intent.getParcelableExtra(EXTRA_POST)
        }

        if (intent.hasExtra(EXTRA_USER)) {
            user = intent.getParcelableExtra(EXTRA_USER)
        }
    }

    fun getCommentList() {
        post?.apply {
            disposables.add(MainDataSource().getComments(getCommentsApiResponse, postId = id))
        }
    }

    fun handleResponseGetComments(response: ApiResponse) {
        when (response.status) {
            StatusApiResponse.LOADING -> showLoading()
            StatusApiResponse.SUCCESS -> {
                showLoading(false)
                handleSuccessGetCommentsResponse(response)
            }
            StatusApiResponse.ERROR -> showLoading(false)
        }
    }

    @VisibleForTesting
    fun convertCommentsResponse(apiResponse: ApiResponse): ArrayList<Comment>? =
        apiResponse.convertToObject<ArrayList<Comment>>()

    @VisibleForTesting
    fun handleSuccessGetCommentsResponse(response: ApiResponse) {
        convertCommentsResponse(response)?.apply {
            comments.value = this
        }
    }
}