package com.example.teste.ui.moviesScreen

import android.graphics.Movie
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.teste.domain.models.MoviesModel
import com.example.teste.domain.repositories.MoviesApiRepository
import com.example.teste.domain.useCases.GetFavoritesMoviesIds
import com.example.teste.domain.useCases.ToggleToFavoriteMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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

    private val isAdultFilter = MutableStateFlow<Boolean?>(null)
    private val selectedRatingFilter = MutableStateFlow<String?>(null)

    private val _moviesFiltered = MutableStateFlow<PagingData<MoviesModel>>(PagingData.empty())
    val moviesFiltered: StateFlow<PagingData<MoviesModel>> get() = _moviesFiltered

    init {
        loadFavoriteMoviesIds()
        setupFilters()
    }

    private fun loadFavoriteMoviesIds() {
        viewModelScope.launch {
             getFavoritesIds.execute()
                 .collect {  ids ->
                     _favoriteIds.value = ids
                 }
        }
    }

    fun setAdultFilter(isAdult: Boolean?) {
        isAdultFilter.value = isAdult
    }

    fun setRatingFilter(filter: String?) {
        selectedRatingFilter.value = filter
    }

    private fun setupFilters() {
        viewModelScope.launch {
            combine(
                searchQuery,
                isAdultFilter,
                selectedRatingFilter,
                movies
            ) { query, isAdult, rating, pagingData ->
                pagingData.filter { movie ->
                    val matchesQuery = movie.title.contains(query, ignoreCase = true)
                    val matchesAdult = isAdult?.let { movie.adult == it } ?: true
                    val matchesRating = when (rating) {
                        "High" -> movie.vote_average >= 8
                        "Medium" -> movie.vote_average >= 5
                        "Low" -> movie.vote_average < 5
                        else -> true
                    }
                    matchesQuery && matchesAdult && matchesRating
                }
            }.cachedIn(viewModelScope).collectLatest { filteredMovies ->
                _moviesFiltered.value = filteredMovies
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