package com.example.movies.repository

import com.example.movies.api.RetrofitInstance

class MoviesRepository {
    suspend fun getAllMovies() =
        RetrofitInstance.API.getAllMovies()

    suspend fun getMovie(query: String) =
        RetrofitInstance.API.getMovie(query)

    suspend fun getMovieDetails(id: Int) =
        RetrofitInstance.API.movieDetails(id)
}