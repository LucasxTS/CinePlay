package com.example.teste.commons.di

import com.example.teste.domain.repositories.FavoriteMoviesRepository
import com.example.teste.domain.repositories.MoviesApiRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MoviesApiRepository(get())}
    single { FavoriteMoviesRepository(get()) }
}