package com.example.movies.api

import com.example.movies.models.MyResponse
import com.example.movies.test.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApi {

    @GET("list_movies.json/")
    suspend fun getAllMovies(
    ) : Response<MyResponse>

    @GET("list_movies.json/")
    suspend fun getMovie(
        @Query("query_term")
        query: String
    ) : Response<MyResponse>

    @GET("movie_details.json/")
    suspend fun movieDetails(
        @Query("movie_id")
        movieId: Int,
        @Query("with_images")
        with_images: Boolean = true,
        @Query("with_cast")
        with_cast: Boolean = true
    ) : Response<MovieDetailsResponse>
}