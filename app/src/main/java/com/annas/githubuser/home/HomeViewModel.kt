package com.annas.githubuser.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.annas.githubuser.core.domain.usecase.UserUseCase

class HomeViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    init {
        username.value = ""
    }

    fun searchQuery(query: String) {
        if (username.value == query) {
            return
        }
        username.value = query
    }

    val users = Transformations.switchMap(username) {
        userUseCase.fetchUsers(it).asLiveData()
    }
}