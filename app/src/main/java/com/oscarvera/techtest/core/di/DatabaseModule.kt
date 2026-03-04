package com.oscarvera.techtest.core.di

import androidx.room.Room
import com.oscarvera.techtest.data.local.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DB_NAME = "techtest_db"

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    single { get<AppDatabase>().favoriteDao() }

}