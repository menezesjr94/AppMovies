package com.example.appmovies.ui.favoritelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmovies.common.core.State
import com.example.appmovies.model.MovieDetails
import com.example.appmovies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<State<List<MovieDetails>>>(State.Empty())
    val favorites: StateFlow<State<List<MovieDetails>>> = _favorites

    init {
        fetchFavoriteMovies()
    }

    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            repository.getAll().collectLatest { movies ->
                if (movies.isEmpty()) {
                    _favorites.value = State.Empty()
                } else {
                    _favorites.value = State.Success(movies)
                }
            }
        }
    }

    fun delete(movieDetails: MovieDetails) = viewModelScope.launch {
        repository.delete(movieDetails)
    }
}