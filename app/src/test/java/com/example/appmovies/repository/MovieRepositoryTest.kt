package com.example.appmovies.repository

import com.example.appmovies.data.local.MovieDAO
import com.example.appmovies.data.remote.MovieDataService
import com.example.appmovies.utils.mockMovieDetails
import com.example.appmovies.utils.mockMoviesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MovieRepositoryTest {

    @MockK
    private lateinit var service: MovieDataService

    @MockK
    private lateinit var dao: MovieDAO

    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        service = mockk(relaxed = true)
        dao = mockk(relaxed = true)
        repository = MovieRepository(service, dao)
    }

    @Test
    fun `getPopularMovies should call service's getPopularMovies`() = runBlocking {
        // Given
        val page = 1
        val movies = mockMoviesResponse()
        coEvery { service.getPopularMovies(page,"k_2iqr8oyf") } returns Response.success(movies)

        // When
        repository.getPopularMovies(page)

        // Then
        coVerify { service.getPopularMovies(page, "k_2iqr8oyf") }
    }

    @Test
    fun `insert should call dao's insert`() = runBlocking {
        // Given
        val movieDetails = mockMovieDetails()

        // When
        repository.insert(movieDetails)

        // Then
        coVerify { dao.insert(movieDetails) }
    }

    @Test
    fun `getAll should call dao's getAll`() = runBlocking {
        // When
        repository.getAll()

        // Then
        coVerify { dao.getAll() }
    }

    @Test
    fun `delete should call dao's delete`() = runBlocking {
        // Given
        val movieDetails = mockMovieDetails()

        // When
        repository.delete(movieDetails)

        // Then
        coVerify { dao.delete(movieDetails) }
    }


}