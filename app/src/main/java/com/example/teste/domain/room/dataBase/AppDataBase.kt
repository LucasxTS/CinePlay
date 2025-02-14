package com.example.teste.domain.room.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teste.domain.room.daoInterface.FavoriteMovieDao
import com.example.teste.domain.room.table.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 3, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}