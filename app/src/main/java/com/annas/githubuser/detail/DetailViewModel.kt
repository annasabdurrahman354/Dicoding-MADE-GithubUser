package com.annas.githubuser.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.annas.githubuser.core.data.Resource
import com.annas.githubuser.core.domain.model.User
import com.annas.githubuser.core.domain.usecase.UserUseCase

class DetailViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getUserDetail(username: String): LiveData<Resource<User>> {
        return userUseCase.getUserDetail(username).asLiveData()
    }

    fun isFavorite(username: String) = userUseCase.isFavorite(username)

    fun setFavorite(user: User, isFavorite: Boolean) {
        userUseCase.setFavorite(user, isFavorite)
    }
}