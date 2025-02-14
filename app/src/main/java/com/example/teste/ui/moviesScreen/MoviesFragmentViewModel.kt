package com.example.teste.ui.moviesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.repositories.MoviesApiRepository
import com.example.teste.domain.useCases.GetFavoritesMoviesIds
import com.example.teste.domain.useCases.ToggleToFavoriteMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoviesFragmentViewModel(
    moviesApiRepository: MoviesApiRepository,
    private val getFavoritesIds: GetFavoritesMoviesIds,
    private val toggleToFavorite: ToggleToFavoriteMovie
): ViewModel() {
    val movies = moviesApiRepository.getPopularMovies().cachedIn(viewModelScope)
    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favorites: StateFlow<Set<Int>> get() = _favoriteIds
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    val moviesFiltered = searchQuery
        .flatMapLatest { query ->
            movies.map { pagingData ->
                pagingData.filter { movie ->
                    movie.title.contains(query, ignoreCase = true)
                }
            }
        }.cachedIn(viewModelScope)

    init {
        loadFavoriteMoviesIds()
    }

    private fun loadFavoriteMoviesIds() {
        viewModelScope.launch {
             getFavoritesIds.execute()
                 .collect {  ids ->
                     _favoriteIds.value = ids
                 }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleToFavorite(movie: MoviesModel) {
        viewModelScope.launch {
            _favoriteIds.value = toggleToFavorite.execute(movie, _favoriteIds.value)
        }
    }
}