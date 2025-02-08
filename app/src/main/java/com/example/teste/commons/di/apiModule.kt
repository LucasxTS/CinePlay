package com.example.teste.commons.di

import com.example.teste.domain.network.TmdbApi
import com.example.teste.domain.network.authIntercepetor.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    single { GsonBuilder().create() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(TmdbApi::class.java)
    }
}

