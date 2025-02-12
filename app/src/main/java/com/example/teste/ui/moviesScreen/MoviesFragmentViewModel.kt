package com.example.teste.ui.moviesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.teste.domain.repositories.MoviesRepository

class MoviesFragmentViewModel(
    private val moviesRepository: MoviesRepository
): ViewModel() {
    val movies = moviesRepository.getPopularMovies().cachedIn(viewModelScope)
}