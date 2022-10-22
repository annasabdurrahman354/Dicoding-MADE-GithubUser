package com.annas.githubuser.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.annas.githubuser.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite ORDER BY login ASC")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favorite: FavoriteEntity)

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE login = :username)")
    fun isFavorite(username: String): LiveData<Boolean>
}
