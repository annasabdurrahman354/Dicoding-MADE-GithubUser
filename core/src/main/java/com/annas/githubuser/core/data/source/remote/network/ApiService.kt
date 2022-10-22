package com.annas.githubuser.core.data.source.remote.network

import com.annas.githubuser.core.data.source.remote.response.SearchUserResponse
import com.annas.githubuser.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String?
    ): UserResponse

    @GET("search/users")
    suspend fun searchUser(
        @Query(value = "q") username: String
    ): SearchUserResponse
}
