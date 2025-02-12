package com.example.teste.domain.network

import com.example.teste.domain.models.ResultsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun popularMovies(@Query("page") page: Int): Response<ResultsResponse>
}