package com.example.appmovies.repository

import com.example.appmovies.BuildConfig.THE_MOVIE_DB_API_KEY
import com.example.appmovies.data.local.MovieDAO
import com.example.appmovies.data.remote.MovieDataService
import com.example.appmovies.model.MovieDetails
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieDataService,
    private val dao: MovieDAO
) {

    private val apiKey = THE_MOVIE_DB_API_KEY

    suspend fun getPopularMovies(page: Int) = service.getPopularMovies(page, apiKey)

    suspend fun insert(movieDetails: MovieDetails) = dao.insert(movieDetails)
    fun getAll() = dao.getAll()
    suspend fun delete(movieDetails: MovieDetails) = dao.delete(movieDetails)
}