package com.example.teste.commons.di

import com.example.teste.domain.repositories.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MoviesRepository(get())}
}