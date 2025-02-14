package com.example.teste.commons.di

import com.example.teste.domain.useCases.GetFavoritesMoviesIds
import com.example.teste.domain.useCases.ToggleToFavoriteMovie
import org.koin.dsl.module

val useCasesModule = module {
    factory { ToggleToFavoriteMovie(get()) }
    factory { GetFavoritesMoviesIds(get()) }
}