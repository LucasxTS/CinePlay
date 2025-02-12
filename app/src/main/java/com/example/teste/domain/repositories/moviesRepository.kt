package com.example.teste.domain.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.network.TmdbApi
import com.example.teste.domain.pagingSource.MoviePagingSource
import kotlinx.coroutines.flow.Flow

class MoviesRepository(private val tmdbApi: TmdbApi) {
    fun getPopularMovies(): Flow<PagingData<MoviesModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(tmdbApi)}
        ).flow
    }
}