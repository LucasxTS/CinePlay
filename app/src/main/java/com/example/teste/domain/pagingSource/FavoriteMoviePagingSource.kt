package com.example.teste.domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.teste.domain.mapper.toMoviesModel
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.room.daoInterface.FavoriteMovieDao
import kotlinx.coroutines.flow.first

class FavoriteMoviesPagingSource(
    private val dao: FavoriteMovieDao
) : PagingSource<Int, MoviesModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel> {
        return try {
            val moviesList = dao.getAllFavoriteMovies().first()
            println("Filmes retornados do DAO: $moviesList")
            val movies = moviesList.map { it.toMoviesModel() }
            LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            println("erro seu pau nas beira")
            println(e.message)
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, MoviesModel>): Int? = null
}
