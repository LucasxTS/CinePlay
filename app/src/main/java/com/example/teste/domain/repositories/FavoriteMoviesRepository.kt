package com.example.teste.domain.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.pagingSource.FavoriteMoviesPagingSource
import com.example.teste.domain.room.daoInterface.FavoriteMovieDao
import com.example.teste.domain.room.table.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteMoviesRepository(private val dao: FavoriteMovieDao) {
    suspend fun addToFavoriteMovies(movie: FavoriteMovie) {
        dao.insertMovie(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovie) {
        dao.deleteMovie(movie)
    }

    fun getFavoriteIds(): Flow<Set<Int>> {
        return dao.getFavoriteMoviesIds()
            .map { it.toSet() }
    }

    fun getAllFavorites(): Flow<PagingData<MoviesModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FavoriteMoviesPagingSource(dao)
            }
        ).flow
    }
}