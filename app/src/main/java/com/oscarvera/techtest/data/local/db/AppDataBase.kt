package com.oscarvera.techtest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oscarvera.techtest.data.local.entity.FavoriteEntity
import com.oscarvera.techtest.data.local.dao.FavoriteDao

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}