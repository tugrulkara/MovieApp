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
class SearchFragmentViewModel @Inject constructor(private val remoteRepository: RemoteRepository):ViewModel() {

    private val movies=MutableLiveData<Resource<MoviesResponse>>()
    val movieList:LiveData<Resource<MoviesResponse>>
    get() = movies

    fun searchMovie(string: String){
        viewModelScope.launch {
            val searchResponse=remoteRepository.searchMovie(string)
            movies.value=searchResponse
        }
    }

}