package com.example.teste.commons.di

import com.example.teste.ui.favoritesMoviesScreen.FavoriteMoviesViewModel
import com.example.teste.ui.moviesScreen.MoviesFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesFragmentViewModel(
        moviesApiRepository = get(),
        getFavoritesIds = get(),
        toggleToFavorite = get(),
    )}
    viewModel { FavoriteMoviesViewModel(
        favoriteMoviesRepository = get(),
        toggleToFavorite = get(),
        getFavorite = get()
    )}
}