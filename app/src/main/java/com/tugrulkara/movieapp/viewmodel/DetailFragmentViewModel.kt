package com.tugrulkara.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugrulkara.movieapp.data.model.CastingResponse
import com.tugrulkara.movieapp.data.model.GenresResponse
import com.tugrulkara.movieapp.data.model.VideosResponse
import com.tugrulkara.movieapp.data.repository.LocalRepository
import com.tugrulkara.movieapp.data.repository.RemoteRepository
import com.tugrulkara.movieapp.data.roomdb.MovieEntity
import com.tugrulkara.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(private val localRepository: LocalRepository,
                                                  private val remoteRepository: RemoteRepository):ViewModel() {


    private val genres=MutableLiveData<Resource<GenresResponse>>()
    val genreList:LiveData<Resource<GenresResponse>>
    get() = genres

    private val videos=MutableLiveData<Resource<VideosResponse>>()
    val videoList:LiveData<Resource<VideosResponse>>
    get() = videos

    private val casts=MutableLiveData<Resource<CastingResponse>>()
    val castList:LiveData<Resource<CastingResponse>>
    get() = casts


    fun insertMovie(movieEntity: MovieEntity){
        viewModelScope.launch {
            movieEntity.let {
                localRepository.insertMovie(it)
            }
        }
    }

    fun deleteMovie(id: Int){
        viewModelScope.launch {
           localRepository.deleteMovie(id)
        }
    }

    fun getFavMovie(id:Int): LiveData<MovieEntity> {
        return localRepository.getFavMovie(id)
    }

    fun getDataGenre(){
        viewModelScope.launch {
            val genreResponse=remoteRepository.genreMovie()
            genres.value=genreResponse
        }
    }

    fun getDataVideos(movieId:Int){
        viewModelScope.launch {
            val videosResponse=remoteRepository.getVideos(movieId)
            videos.value=videosResponse
        }
    }

    fun getCast(movieId: Int){
        viewModelScope.launch {
            val castResponse=remoteRepository.getCasting(movieId)
            casts.value=castResponse
        }

    }

}