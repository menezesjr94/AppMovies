package com.example.appmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appmovies.model.MovieDetails

@Database(entities = [MovieDetails::class], version = 1, exportSchema = false)
@TypeConverters(MovieConverters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDAO
}