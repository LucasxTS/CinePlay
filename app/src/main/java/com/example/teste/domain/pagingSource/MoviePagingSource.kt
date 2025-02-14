package com.example.teste.domain.pagingSource

import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.network.TmdbApi


class MoviePagingSource(private val tmdbApi: TmdbApi):
    PagingSource<Int, MoviesModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel> {
        return try {
            val page = params.key ?: 1
            val response = tmdbApi.popularMovies(page)

            if (!response.isSuccessful) {
                LoadState.Error(Exception("Api Error."))
            }
            val movies = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page -1,
                nextKey = if (movies.isEmpty()) null else page +1
            )
        } catch (e: Exception) {
           LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, MoviesModel>): Int? = null
}