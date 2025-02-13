package com.example.teste.domain.repositories

import com.example.teste.domain.room.daoInterface.FavoriteMovieDao
import com.example.teste.domain.room.table.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toSet

class FavoriteMoviesRepository(private val dao: FavoriteMovieDao) {
    suspend fun addToFavoriteMovies(movie: FavoriteMovie) {
        dao.insertMovie(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovie) {
        dao.deleteMovie(movie)
    }

    suspend fun getFavoriteIds(): Set<List<Int>> {
        return dao.getFavoriteMoviesIds().toSet()
    }

    fun getAllFavorites(): Flow<List<FavoriteMovie>> {
        return dao.getAllFavoriteMovies()
    }
}