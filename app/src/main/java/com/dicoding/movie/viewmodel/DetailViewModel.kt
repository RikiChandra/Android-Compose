package com.dicoding.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.movie.componet.UiState
import com.dicoding.movie.model.Movie
import com.dicoding.movie.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository): ViewModel()  {

    private val _Ui_state: MutableStateFlow<UiState<Movie>> =
        MutableStateFlow(UiState.Loading)

    val uiState: MutableStateFlow<UiState<Movie>> get() = _Ui_state

    fun getMovieId(id: Int) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            uiState.value = UiState.Success(repository.getMovieById(id))
        }
    }

}