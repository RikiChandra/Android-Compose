package com.dicoding.movie.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.movie.componet.UiState
import com.dicoding.movie.model.Movie
import com.dicoding.movie.repository.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _Uistate: MutableStateFlow<UiState<List<Movie>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movie>>> get() = _Uistate

    private val _movie = MutableStateFlow<Map<Char, List<Movie>>>(emptyMap())
    val movie: StateFlow<Map<Char, List<Movie>>> get() = _movie


    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> get() = _searchQuery



    fun search(query: String) {
        _searchQuery.value = query
        _Uistate.value = UiState.Loading
        _movie.value = movieRepository.searchMovie(_searchQuery.value).groupBy { it.title.firstOrNull()?.uppercaseChar() ?: '#' }
        _Uistate.value = UiState.Success(movieRepository.searchMovie(_searchQuery.value))
    }



    fun getMovie(){
        viewModelScope.launch {
            movieRepository.getMovieAll().onStart {
                _Uistate.value = UiState.Loading
            }.catch {
                _Uistate.value = UiState.Error(it)
            }.collect {
                _Uistate.value = UiState.Success(it)
            }
        }
    }



}