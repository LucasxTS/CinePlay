package com.example.teste.domain.useCases

import com.example.teste.domain.repositories.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesMoviesIds(private val favoriteMoviesRepository: FavoriteMoviesRepository) {
    fun execute(): Flow<Set<Int>> {
        return favoriteMoviesRepository.getFavoriteIds()
    }
}