package com.example.appmovies.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmovies.model.MovieDetails
import com.example.appmovies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    fun insert(movieDetails: MovieDetails) = viewModelScope.launch {
        repository.insert(movieDetails)
    }
}