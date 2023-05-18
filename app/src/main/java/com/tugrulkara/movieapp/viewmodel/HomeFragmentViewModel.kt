package com.tugrulkara.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugrulkara.movieapp.data.model.MoviesResponse
import com.tugrulkara.movieapp.data.repository.RemoteRepository
import com.tugrulkara.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val remoteRepository: RemoteRepository):ViewModel() {

    private val movies= MutableLiveData<Resource<MoviesResponse>>()
    val movieList : LiveData<Resource<MoviesResponse>>
    get()=movies

    private val nowPlayingMovies= MutableLiveData<Resource<MoviesResponse>>()
    val nowPlayingList : LiveData<Resource<MoviesResponse>>
    get()=nowPlayingMovies


    fun getMovies(){
        viewModelScope.launch {
            val trendingResponse=remoteRepository.getMovies()
            movies.value=trendingResponse
        }
    }

    fun getNowPlaying(){
        viewModelScope.launch {
            val nowPlayingResponse=remoteRepository.getNowPlayingMovies()
            nowPlayingMovies.value=nowPlayingResponse
        }
    }
}