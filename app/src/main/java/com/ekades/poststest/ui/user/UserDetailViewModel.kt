package com.ekades.poststest.ui.user

import android.content.Intent
import androidx.annotation.VisibleForTesting
import com.ekades.poststest.lib.application.viewmodel.BaseViewModel
import com.ekades.poststest.lib.application.viewmodel.mutableLiveDataOf
import com.ekades.poststest.networks.dataSource.MainDataSource
import com.ekades.poststest.lib.core.network.responses.ApiResponse
import com.ekades.poststest.lib.core.network.responses.constant.StatusApiResponse
import com.ekades.poststest.models.Album
import com.ekades.poststest.models.Comment
import com.ekades.poststest.models.Photo
import com.ekades.poststest.models.User
import com.ekades.poststest.ui.post.PostDetailActivity

class UserDetailViewModel : BaseViewModel() {

    var getAlbumsApiResponse = mutableLiveDataOf<ApiResponse>()
    var albums: ArrayList<Album>? = null

    var getPhotosApiResponse = mutableLiveDataOf<ApiResponse>()

    val userAlbums = mutableLiveDataOf<ArrayList<Album>>()
    var temporaryUserAlbums: ArrayList<Album> = arrayListOf()

    var user: User? = null
    var getPhotosAlbumIndex = 0
    var maxAlbumIndex = 2

    fun processIntent(intent: Intent) {
        if (intent.hasExtra(PostDetailActivity.EXTRA_USER)) {
            user = intent.getParcelableExtra(PostDetailActivity.EXTRA_USER)
        }
    }

    fun getAlbumList() {
        user?.apply {
            disposables.add(MainDataSource().getAlbums(getAlbumsApiResponse, userId = id))
        }
    }

    fun handleResponseGetAlbums(response: ApiResponse) {
        when (response.status) {
            StatusApiResponse.LOADING -> showLoading()
            StatusApiResponse.SUCCESS -> {
                showLoading(false)
                handleSuccessGetAlbumsResponse(response)
            }
            StatusApiResponse.ERROR -> showLoading(false)
        }
    }

    @VisibleForTesting
    fun convertAlbumsResponse(apiResponse: ApiResponse): ArrayList<Album>? =
        apiResponse.convertToObject<ArrayList<Album>>()

    @VisibleForTesting
    fun handleSuccessGetAlbumsResponse(response: ApiResponse) {
        convertAlbumsResponse(response)?.apply {
            albums = this
            getPhotos()
        }
    }

    fun getPhotos() {
        albums?.getOrNull(getPhotosAlbumIndex)?.apply {
            disposables.add(MainDataSource().getPhotos(getPhotosApiResponse, albumId = id))
        }
    }

    fun handleResponseGetPhotos(response: ApiResponse) {
        when (response.status) {
            StatusApiResponse.LOADING -> showLoading()
            StatusApiResponse.SUCCESS -> {
                showLoading(false)
                handleSuccessGetPhotosResponse(response)
            }
            StatusApiResponse.ERROR -> showLoading(false)
        }
    }

    @VisibleForTesting
    fun convertPhotosResponse(apiResponse: ApiResponse): ArrayList<Photo>? =
        apiResponse.convertToObject<ArrayList<Photo>>()

    @VisibleForTesting
    fun handleSuccessGetPhotosResponse(response: ApiResponse) {
        convertPhotosResponse(response)?.apply {
            updateUserAlbums(this)
        }
    }

    private fun updateUserAlbums(photoList: ArrayList<Photo>) {
        albums?.getOrNull(getPhotosAlbumIndex)?.apply {
            photos = photoList
            temporaryUserAlbums.add(this)
            if (getPhotosAlbumIndex >= maxAlbumIndex) {
                val newList = userAlbums.value ?: arrayListOf()
                newList.addAll(temporaryUserAlbums)
                userAlbums.value = newList
                temporaryUserAlbums.clear()
            } else {
                getPhotosAlbumIndex++
                getPhotos()
            }
        }
    }
}