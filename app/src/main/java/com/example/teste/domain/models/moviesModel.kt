package com.example.teste.domain.models

import java.io.Serializable

data class ResultsResponse(
    val results: List<MoviesModel>
)
data class MoviesModel(
    val id: Int,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val overview: String
): Serializable
