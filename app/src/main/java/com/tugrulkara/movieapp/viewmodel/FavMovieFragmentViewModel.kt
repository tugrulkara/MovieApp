package com.tugrulkara.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugrulkara.movieapp.data.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavMovieFragmentViewModel @Inject constructor(private val localRepository: LocalRepository):ViewModel() {


    val movies=localRepository.getMovies()

    fun deleteFavMovie(id:Int){
        viewModelScope.launch {
            localRepository.deleteMovie(id)
        }
    }

}