package com.example.teste.domain.useCases

import com.example.teste.domain.mapper.toFavoriteMovie
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.repositories.FavoriteMoviesRepository
import kotlinx.coroutines.flow.first

class ToggleToFavoriteMovie(private val favoriteMoviesRepository: FavoriteMoviesRepository) {
    suspend fun execute(movie: MoviesModel,currentFavoriteIds: Set<Int>): Set<Int> {
        val movieId = movie.id
        if (movieId in currentFavoriteIds) {
            favoriteMoviesRepository.removeFavorite(movie.toFavoriteMovie())
            return favoriteMoviesRepository.getFavoriteIds().first()
        }
        favoriteMoviesRepository.addToFavoriteMovies(movie.toFavoriteMovie())
        return favoriteMoviesRepository.getFavoriteIds().first()
    }
}
