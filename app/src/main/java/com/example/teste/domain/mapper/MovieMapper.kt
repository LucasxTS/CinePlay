package com.example.teste.domain.mapper

import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.room.table.FavoriteMovie

fun MoviesModel.toFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        id = this.id,
        title = this.title,
        backdropPath = this.backdrop_path,
        releaseDate = this.release_date,
        voteAverage = this.vote_average,
        overview = this.overview,
        poster_path = this.poster_path,
        adult = this.adult
    )
}

fun FavoriteMovie.toMoviesModel(): MoviesModel {
    return MoviesModel(
        id = this.id,
        backdrop_path = this.backdropPath,
        poster_path = this.poster_path,
        release_date = this.releaseDate,
        title = this.title,
        vote_average = this.voteAverage,
        overview = this.overview,
        adult = this.adult
    )
}