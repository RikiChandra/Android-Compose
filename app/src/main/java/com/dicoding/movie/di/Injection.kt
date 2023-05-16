package com.dicoding.movie.di

import com.dicoding.movie.repository.MovieRepository

object Injection {
    fun provideMovieRepository(): MovieRepository {
        return MovieRepository.getInstance()
    }
}