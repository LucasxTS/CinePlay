package com.example.teste.ui.moviesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teste.domain.repositories.MoviesRepository
import kotlinx.coroutines.launch

class MoviesFragmentViewModel(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private var pagesForGetPopularFilms = 1

    fun fetchMovies() {
        viewModelScope.launch {
            moviesRepository.getPopularMovies(pagesForGetPopularFilms)
                .collect { result ->
                    result.onFailure {
                        println(it.message)
                        println(it.cause)
                        println(it.cause)
                    }
                    result.onSuccess {
                        println(it?.results)
                    }
                }
        }
    }
}