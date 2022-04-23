package com.ekades.poststest.features.users.data.dataSource.interfaces
import com.ekades.poststest.features.users.data.dataSource.response.UserResponseData
import retrofit2.http.GET
import retrofit2.http.Path

interface IUserApiClient {
    @GET("/users")
    suspend fun getUsers(): List<UserResponseData>

    @GET("/users/{login}")
    suspend fun getUserByLogin(@Path("login") login:String): UserResponseData
}