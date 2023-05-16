package com.dicoding.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.movie.repository.MovieRepository

class ViewModelFactory(private val repo : MovieRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel(repo).javaClass)) {
            return MainViewModel(repo) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel(repo).javaClass)) {
            return DetailViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }



}