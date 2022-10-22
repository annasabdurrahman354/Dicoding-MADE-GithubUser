package com.annas.githubuser.core.data

import androidx.lifecycle.LiveData
import com.annas.githubuser.core.data.source.local.LocalDataSource
import com.annas.githubuser.core.data.source.remote.RemoteDataSource
import com.annas.githubuser.core.data.source.remote.network.ApiResponse
import com.annas.githubuser.core.data.source.remote.response.UserResponse
import com.annas.githubuser.core.domain.model.User
import com.annas.githubuser.core.domain.repository.IUserRepository
import com.annas.githubuser.core.utils.AppExecutors
import com.annas.githubuser.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {

    override fun fetchUsers(username: String): Flow<Resource<List<User>>> =
        object : NetworkResource<List<User>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                if (username == ""){
                    return remoteDataSource.getUsers()
                }
                return remoteDataSource.searchUser(username)
            }
        }.asFlow()

    override fun getUserDetail(username: String): Flow<Resource<User>> {
        return object : NetworkResource<User, UserResponse>() {
            override fun loadFromNetwork(data: UserResponse): Flow<User> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getUserDetail(username)
            }

        }.asFlow()
    }

    override fun getFavorites(): Flow<List<User>> {
        return localDataSource.getFavorites().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavorite(user: User, status: Boolean) {
        val userEntity = DataMapper.mapDomainToEntity(user)
        appExecutors.diskIO().execute { localDataSource.setFavorite(userEntity, status) }
    }

    override fun isFavorite(username: String): LiveData<Boolean> {
        return localDataSource.isFavorite(username)
    }
}

