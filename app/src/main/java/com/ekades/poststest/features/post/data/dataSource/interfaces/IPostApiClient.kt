package com.ekades.poststest.features.post.data.dataSource.interfaces
import com.ekades.poststest.features.post.data.dataSource.response.PostResponseData
import retrofit2.http.GET
import retrofit2.http.Path

interface IPostApiClient {
    @GET("/posts")
    suspend fun getPosts(): List<PostResponseData>

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id:String): PostResponseData
}