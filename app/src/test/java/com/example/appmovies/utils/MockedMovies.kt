package com.example.appmovies.utils

import com.example.appmovies.model.MovieDetails
import com.example.appmovies.model.MoviesResponse

fun mockMoviesResponse(): MoviesResponse {
    val movie1 = MovieDetails(
        id = 1,
        adult = false,
        backdropPath = "backdrop1.jpg",
        genreIds = arrayListOf(1, 2, 3),
        originalLanguage = "en",
        originalTitle = "Movie 1",
        overview = "Overview 1",
        popularity = 7.8,
        posterPath = "poster1.jpg",
        releaseDate = "2023-06-01",
        title = "Movie 1",
        voteAverage = 7.5,
        voteCount = 100
    )

    val movie2 = MovieDetails(
        id = 2,
        adult = false,
        backdropPath = "backdrop2.jpg",
        genreIds = arrayListOf(4, 5),
        originalLanguage = "en",
        originalTitle = "Movie 2",
        overview = "Overview 2",
        popularity = 8.2,
        posterPath = "poster2.jpg",
        releaseDate = "2023-06-02",
        title = "Movie 2",
        voteAverage = 8.0,
        voteCount = 200
    )

    val movieList = listOf(movie1, movie2)

    return MoviesResponse(movieList, 1, 1)
}

fun mockMovieDetails(): MovieDetails {
    return MovieDetails(
        id = 1,
        adult = false,
        backdropPath = "backdrop1.jpg",
        genreIds = arrayListOf(1, 2, 3),
        originalLanguage = "en",
        originalTitle = "Movie 1",
        overview = "Overview 1",
        popularity = 7.8,
        posterPath = "poster1.jpg",
        releaseDate = "2023-06-01",
        title = "Movie 1",
        voteAverage = 7.5,
        voteCount = 100
    )
}