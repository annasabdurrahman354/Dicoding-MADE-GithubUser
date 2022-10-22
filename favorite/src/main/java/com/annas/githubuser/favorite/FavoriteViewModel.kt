package com.annas.githubuser.favorite

import androidx.lifecycle.ViewModel
import com.annas.githubuser.core.domain.usecase.UserUseCase
import androidx.lifecycle.asLiveData

class FavoriteViewModel(userUseCase: UserUseCase) : ViewModel() {
    val favoriteUsers = userUseCase.getFavorite().asLiveData()
}