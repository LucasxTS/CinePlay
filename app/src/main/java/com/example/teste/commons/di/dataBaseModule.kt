package com.example.teste.commons.di

import androidx.room.Room
import com.example.teste.domain.room.dataBase.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "movie_database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDataBase>().favoriteMovieDao() }
}