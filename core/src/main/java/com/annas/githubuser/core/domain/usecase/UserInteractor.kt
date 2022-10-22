package com.annas.githubuser.core.domain.usecase

import com.annas.githubuser.core.domain.model.User
import com.annas.githubuser.core.domain.repository.IUserRepository

class UserInteractor(private val userRepository: IUserRepository): UserUseCase {
    override fun fetchUsers(username: String) = userRepository.fetchUsers(username)
    override fun getUserDetail(username: String) = userRepository.getUserDetail(username)
    override fun getFavorite() = userRepository.getFavorites()
    override fun setFavorite(user: User, state: Boolean) = userRepository.setFavorite(user, state)
    override fun isFavorite (username: String) = userRepository.isFavorite(username)
}