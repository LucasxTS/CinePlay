package com.example.teste.commons.di

import com.example.teste.ui.moviesScreen.MoviesFragmentViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MoviesFragmentViewModel(get()) }
}