package com.example.teste.ui.favoritesMoviesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.repositories.FavoriteMoviesRepository
import com.example.teste.domain.useCases.GetFavoritesMoviesIds
import com.example.teste.domain.useCases.ToggleToFavoriteMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    private val toggleToFavorite: ToggleToFavoriteMovie,
    private val getFavorite: GetFavoritesMoviesIds
): ViewModel() {
    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favorites: StateFlow<Set<Int>> get() = _favoriteIds
    private val _movies = MutableStateFlow<PagingData<MoviesModel>>(PagingData.empty())
    val movies: StateFlow<PagingData<MoviesModel>> get() = _movies

    init {
        viewModelScope.launch {
            loadFavoriteMoviesIds()
            reloadFavoriteMovies()
        }
    }

    private fun loadFavoriteMoviesIds() {
        viewModelScope.launch {
            getFavorite.execute()
                .collect { ids ->
                    _favoriteIds.value = ids
                    println(_favoriteIds.value)
                }
        }
    }

    fun toggleToFavorite(movie: MoviesModel) {
        viewModelScope.launch {
            _favoriteIds.value = toggleToFavorite.execute(movie, _favoriteIds.value)
               reloadFavoriteMovies()
        }
    }

    private fun reloadFavoriteMovies() {
        viewModelScope.launch {
            favoriteMoviesRepository.getAllFavorites().cachedIn(viewModelScope).collectLatest { updatedMovies ->
                _movies.value = updatedMovies
            }
        }
    }
}