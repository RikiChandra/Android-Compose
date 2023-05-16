package com.dicoding.movie.repository

import com.dicoding.movie.model.Movie
import com.dicoding.movie.model.MovieData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MovieRepository {

    private val movieData = mutableListOf<Movie>()

    fun getMovieAll(): Flow<List<Movie>>{
        return flowOf(movieData)
    }

    fun getMovieById(id: Int): Movie{
        return movieData.first{
            it.id == id
        }
    }

    fun searchMovie(query: String): List<Movie> {
        return movieData.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }




    init {
        if(movieData.isEmpty()){
            MovieData.movie.forEach {
                movieData.add(it)
            }
        }
    }

    companion object{
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository =
            instance ?: synchronized(this){
                instance ?: MovieRepository().apply {
                    instance = this
                }
            }
    }


}