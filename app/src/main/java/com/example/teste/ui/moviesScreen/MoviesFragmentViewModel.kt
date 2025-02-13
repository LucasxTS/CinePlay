package com.example.teste.ui.moviesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.teste.domain.repositories.FavoriteMoviesRepository
import com.example.teste.domain.repositories.MoviesApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MoviesFragmentViewModel(
    private val moviesApiRepository: MoviesApiRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository
): ViewModel() {
    val movies = moviesApiRepository.getPopularMovies().cachedIn(viewModelScope)
    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favorites: StateFlow<Set<Int>> get() = _favoriteIds

}