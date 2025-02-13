package com.example.teste.commons.di

import com.example.teste.ui.moviesScreen.MoviesFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesFragmentViewModel(moviesApiRepository = get(), favoriteMoviesRepository = get())}
}