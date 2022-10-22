package com.annas.githubuser.core.domain.usecase

import androidx.lifecycle.LiveData
import com.annas.githubuser.core.data.Resource
import com.annas.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun fetchUsers(username: String): Flow<Resource<List<User>>>
    fun getUserDetail(username: String): Flow<Resource<User>>
    fun getFavorite(): Flow<List<User>>
    fun setFavorite(user: User, state: Boolean)
    fun isFavorite(username: String) : LiveData<Boolean>
}