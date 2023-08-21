package com.example.appmovies.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmovies.common.core.State
import com.example.appmovies.model.MoviesResponse
import com.example.appmovies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<State<MoviesResponse>>(State.Loading())
    val movies: StateFlow<State<MoviesResponse>> = _movies

    private var currentPage = 1
    private var totalPages = 0
    var isLoadingNextPage = false

    init {
        fetchMovies(currentPage)
    }

    fun nextPage() {
        if (currentPage < totalPages) {
            currentPage++
            fetchMovies(currentPage)
        }
    }

    fun fetchMovies(page: Int) {
        viewModelScope.launch {
            isLoadingNextPage = true

            val moviesResponse = repository.getPopularMovies(page)
            totalPages = moviesResponse.body()?.totalPages ?: 1
            _movies.value = handleResult(moviesResponse)

            isLoadingNextPage = false
        }
    }

    private fun handleResult(response: Response<MoviesResponse>): State<MoviesResponse> {
        if (response.isSuccessful) {
            response.body()?.let { data ->
                return State.Success(data = data)
            }
        }
        return State.Error(response.message())
    }
}