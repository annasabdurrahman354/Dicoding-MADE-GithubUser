package com.annas.githubuser.core.utils

import com.annas.githubuser.core.data.source.local.entity.FavoriteEntity
import com.annas.githubuser.core.data.source.remote.response.UserResponse
import com.annas.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: List<UserResponse>): Flow<List<User>> {
        val dataArray = ArrayList<User>()
        input.map {
            val user = User(
                it.id,
                it.username,
                it.avatarUrl,
                it.name,
                it.company,
                it.location,
                it.publicRepos,
                it.followers,
                it.following,
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapResponseToDomain(input: UserResponse): Flow<User> {
        return flowOf(
            User(
                input.id, input.username, input.avatarUrl, input.name, input.company, input.location, input.publicRepos, input.followers, input.following
            )
        )
    }

    fun mapEntitiesToDomain(input: List<FavoriteEntity>): List<User> =
        input.map {
            User(
                id = it.id,
                username = it.username,
                avatarUrl = it.avatarUrl,
            )
        }

    fun mapDomainToEntity(input: User) = FavoriteEntity(
        id = input.id,
        username = input.username,
        avatarUrl = input.avatarUrl,
    )
}