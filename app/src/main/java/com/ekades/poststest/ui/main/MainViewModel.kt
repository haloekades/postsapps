package com.ekades.poststest.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MediatorLiveData
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.poststest.networks.dataSource.MainDataSource
import com.ekades.poststest.models.Post
import com.ekades.poststest.lib.core.network.responses.ApiResponse
import com.ekades.poststest.lib.core.network.responses.constant.StatusApiResponse
import com.ekades.poststest.models.User
import kotlin.collections.ArrayList

class MainViewModel : BaseViewModel() {

    var getPostsApiResponse = mutableLiveDataOf<ApiResponse>()
    val posts = mutableLiveDataOf<ArrayList<Post>>()

    var getUsersApiResponse = mutableLiveDataOf<ApiResponse>()
    val users = mutableLiveDataOf<ArrayList<User>>()

    val mediatorPostUsers = MediatorLiveData<ArrayList<Post>>()

    init {
        initShownPostUsers()
    }

    private fun initShownPostUsers() {
        var isLoadedPosts = false
        var isLoadedUsers = false

        with(mediatorPostUsers) {
            addSource(posts) {
                isLoadedPosts = true

                if (isLoadedPosts && isLoadedUsers) {
                    value = getShownPostUsers()
                }
            }

            addSource(users) {
                isLoadedUsers = true

                if (isLoadedPosts && isLoadedUsers) {
                    value = getShownPostUsers()
                }
            }
        }
    }

    private fun getShownPostUsers(): ArrayList<Post> {
        val result = posts.value

        result?.forEach {
            getUserById(it.userId)?.apply {
                it.userName = username
                it.userCompanyName = company.name
            }
        }

        return result ?: arrayListOf()
    }

    fun getUserById(userId: Int): User? {
        return users.value?.find { it.id == userId }
    }

    fun getPostList() {
        disposables.add(MainDataSource().getPosts(getPostsApiResponse))
    }

    fun handleResponseGetPosts(response: ApiResponse) {
        when (response.status) {
            StatusApiResponse.SUCCESS -> handleSuccessGetPostsResponse(response)
            StatusApiResponse.ERROR -> showLoading(false)
        }
    }

    @VisibleForTesting
    fun convertPostsResponse(apiResponse: ApiResponse): ArrayList<Post>? =
        apiResponse.convertToObject<ArrayList<Post>>()

    @VisibleForTesting
    fun handleSuccessGetPostsResponse(response: ApiResponse) {
        convertPostsResponse(response)?.apply {
            posts.value = this
        }
    }

    fun getUserList() {
        disposables.add(MainDataSource().getUsers(getUsersApiResponse))
    }

    fun handleResponseGetUsers(response: ApiResponse) {
        when (response.status) {
            StatusApiResponse.SUCCESS -> handleSuccessGetUsersResponse(response)
            StatusApiResponse.ERROR -> showLoading(false)
        }
    }

    @VisibleForTesting
    fun convertUsersResponse(apiResponse: ApiResponse): ArrayList<User>? =
        apiResponse.convertToObject<ArrayList<User>>()

    @VisibleForTesting
    fun handleSuccessGetUsersResponse(response: ApiResponse) {
        convertUsersResponse(response)?.apply {
            users.value = this
        }
    }
}