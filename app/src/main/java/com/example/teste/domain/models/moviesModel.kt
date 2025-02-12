package com.example.teste.domain.models

data class ResultsResponse(
    val results: List<MoviesModel>
)

data class MoviesModel(
    val id: Int,
    val original_title: String,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double
)
