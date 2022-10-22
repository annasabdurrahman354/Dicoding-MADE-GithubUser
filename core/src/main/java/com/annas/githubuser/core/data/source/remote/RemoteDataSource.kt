package com.annas.githubuser.core.data.source.remote

import android.util.Log
import com.annas.githubuser.core.data.source.remote.network.ApiResponse
import com.annas.githubuser.core.data.source.remote.network.ApiService
import com.annas.githubuser.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getUsers(): Flow<ApiResponse<List<UserResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getUsers()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Log.e("RemoteDataSource", "Not Empty")
                } else {
                    emit(ApiResponse.Empty)
                    Log.e("RemoteDataSource","Empty")
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchUser(username: String): Flow<ApiResponse<List<UserResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.searchUser(username).items
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Log.e("RemoteDataSource", "Not Empty")
                } else {
                    emit(ApiResponse.Empty)
                    Log.e("RemoteDataSource","Empty")
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(username: String): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val userDetail = apiService.getUserDetail(username)
                emit(ApiResponse.Success(userDetail))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)
    }

}

