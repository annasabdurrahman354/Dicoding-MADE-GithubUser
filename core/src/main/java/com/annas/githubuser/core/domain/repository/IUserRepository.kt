package com.annas.githubuser.core.domain.repository

import androidx.lifecycle.LiveData
import com.annas.githubuser.core.data.Resource
import com.annas.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getUserDetail(username: String): Flow<Resource<User>>
    fun getFavorites(): Flow<List<User>>
    fun setFavorite(user: User, state: Boolean)
    fun isFavorite(username: String) : LiveData<Boolean>
    fun fetchUsers(username: String): Flow<Resource<List<User>>>
}