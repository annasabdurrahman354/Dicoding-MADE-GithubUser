package com.annas.githubuser.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.annas.githubuser.core.data.source.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}