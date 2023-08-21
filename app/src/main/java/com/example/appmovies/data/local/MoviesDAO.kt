package com.example.appmovies.data.local

import androidx.room.*
import com.example.appmovies.model.MovieDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetails: MovieDetails) : Long

    @Query("SELECT * FROM movieDetail ORDER BY id")
    fun getAll() : Flow<List<MovieDetails>>

    @Delete
    suspend fun delete(movieDetails: MovieDetails)
}