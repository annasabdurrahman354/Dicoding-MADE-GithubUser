package com.annas.githubuser.core.data.source.local

import androidx.lifecycle.LiveData
import com.annas.githubuser.core.data.source.local.entity.FavoriteEntity
import com.annas.githubuser.core.data.source.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favoriteDao: FavoriteDao) {
    fun getFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getFavorites()

    fun setFavorite(user: FavoriteEntity, status: Boolean) {
        if (!status){
            favoriteDao.addFavorite(user)
        }
        else{
            favoriteDao.deleteFavorite(user)
        }
    }

    fun isFavorite(username: String): LiveData<Boolean> {
        return favoriteDao.isFavorite(username)
    }
}