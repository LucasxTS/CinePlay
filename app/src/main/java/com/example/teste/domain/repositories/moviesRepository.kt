package com.example.teste.domain.repositories

import com.example.teste.domain.models.ResultsResponse
import com.example.teste.domain.network.TmdbApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepository(private val tmdbApi: TmdbApi) {
    fun getPopularMovies(page: Int): Flow<Result<ResultsResponse?>> = flow {
        val response = try {
            tmdbApi.popularMovies(page)
        } catch (e: Exception) {
            emit(Result.failure(e))
            return@flow
        }

        if (!response.isSuccessful || response.body() == null) {
            emit(Result.failure(Exception("Erro na API: ${response.message()}")))
            return@flow
        }

        emit(Result.success(response.body()))
    }
}