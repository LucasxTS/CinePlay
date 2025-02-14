package com.example.teste.domain.room.daoInterface

import androidx.paging.PagingData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teste.domain.room.table.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): Flow<List<FavoriteMovie>>

    @Query("SELECT id FROM favorite_movies")
    fun getFavoriteMoviesIds(): Flow<List<Int>>
}