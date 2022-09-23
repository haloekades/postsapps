package com.ekades.fcmpushnotification.networks.dataSource

import androidx.lifecycle.MutableLiveData
import com.ekades.fcmpushnotification.networks.PathUrls
import com.ekades.fcmpushnotification.lib.core.network.RemoteDataSource
import com.ekades.fcmpushnotification.lib.core.network.responses.ApiResponse
import com.ekades.fcmpushnotification.lib.core.network.utils.constants.ApiMethod
import io.reactivex.disposables.Disposable

class MainDataSource(method: ApiMethod = ApiMethod.GET) :
    RemoteDataSource(method) {

    fun getPosts(liveData: MutableLiveData<ApiResponse>): Disposable {
        path = PathUrls.POSTS
        return execute(liveData)
    }

    fun getUsers(liveData: MutableLiveData<ApiResponse>): Disposable {
        path = PathUrls.USERS
        return execute(liveData)
    }

    fun getComments(liveData: MutableLiveData<ApiResponse>, postId: Int): Disposable {
        path = "${PathUrls.COMMENTS}?$PARAM_POST_ID=$postId"
        return execute(liveData)
    }

    fun getAlbums(liveData: MutableLiveData<ApiResponse>, userId: Int): Disposable {
        path = "${PathUrls.ALBUMS}?$PARAM_USER_ID=$userId"
        return execute(liveData)
    }

    fun getPhotos(liveData: MutableLiveData<ApiResponse>, albumId: Int): Disposable {
        path = "${PathUrls.PHOTOS}?$PARAM_ALBUM_ID=$albumId"
        return execute(liveData)
    }

    companion object {
        const val PARAM_POST_ID = "postId"
        const val PARAM_USER_ID = "userId"
        const val PARAM_ALBUM_ID = "albumId"
    }
}