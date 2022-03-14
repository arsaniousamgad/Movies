package com.example.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movies.models.MyResponse
import com.example.movies.repository.MoviesRepository
import com.example.movies.test.MovieDetailsResponse
import com.example.movies.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel(val moviesRepository : MoviesRepository) : ViewModel() {

    val allMovies : MutableLiveData<Resource<MyResponse>> = MutableLiveData()
    var allMovieResponse : MyResponse? = null

    val movieSearch : MutableLiveData<Resource<MyResponse>> = MutableLiveData()
    var movieSearchResponse : MyResponse? = null

    val movieDetails : MutableLiveData<Resource<MovieDetailsResponse>> = MutableLiveData()
    var movieDetailsResponse : MovieDetailsResponse? = null

    fun getAllMovies() = viewModelScope.launch {
        allMovies.postValue(Resource.Loading())
        val response = moviesRepository.getAllMovies()
        allMovies.postValue(handleMovieResponse(response))
    }

    fun getMovie(query: String) = viewModelScope.launch {
        movieSearch.postValue(Resource.Loading())
        val response = moviesRepository.getMovie(query)
        movieSearch.postValue(handleSearchMovieResponse(response))
    }

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        movieDetails.postValue(Resource.Loading())
        val response = moviesRepository.getMovieDetails(movieId)
        movieDetails.postValue(handleMovieDetailsResponse(response))
    }

    private fun handleMovieResponse(response: Response<MyResponse>) : Resource<MyResponse> {
        if (response.isSuccessful){
            response.body().let {
                if (allMovieResponse == null){
                    allMovieResponse = it
                }
            }
            return Resource.Success(allMovieResponse)
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchMovieResponse(response: Response<MyResponse>) : Resource<MyResponse> {
        if (response.isSuccessful) {
            response.body().let {
                movieSearchResponse = it
            }
            return Resource.Success(movieSearchResponse)
        }
        return Resource.Error(response.message())
    }

    private fun handleMovieDetailsResponse(response: Response<MovieDetailsResponse>) : Resource<MovieDetailsResponse> {
        if (response.isSuccessful){
            response.body().let {
                movieDetailsResponse = it
            }
            return Resource.Success(movieDetailsResponse)
        }
        return Resource.Error(response.message())
    }
}

class MoviesViewModelFactory(val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepository) as T
    }
}