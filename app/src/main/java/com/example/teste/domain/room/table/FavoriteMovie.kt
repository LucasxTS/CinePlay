package com.example.teste.domain.room.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val poster_path: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val adult: Boolean
)