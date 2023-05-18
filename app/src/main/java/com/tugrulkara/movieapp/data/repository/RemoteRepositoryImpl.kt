package com.tugrulkara.movieapp.data.repository

import com.tugrulkara.movieapp.data.model.CastingResponse
import com.tugrulkara.movieapp.data.model.GenresResponse
import com.tugrulkara.movieapp.data.model.MoviesResponse
import com.tugrulkara.movieapp.data.model.VideosResponse
import com.tugrulkara.movieapp.data.service.MainService
import com.tugrulkara.movieapp.util.Resource
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val mainService: MainService):RemoteRepository {

    override suspend fun getMovies(): Resource<MoviesResponse> {
        return try {
            val response= mainService.getMovies()
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e: Exception){
            Resource.error("No Data",null)
        }
    }

    override suspend fun searchMovie(string: String): Resource<MoviesResponse> {
        return try {
            val response=mainService.searchMovie(string)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("No Data",null)
            }else{
                Resource.error("No Data",null)
            }
        }catch (e:Exception){
            Resource.error("No Data",null)
        }
    }

    override suspend fun genreMovie(): Resource<GenresResponse> {
        return try {
            val response=mainService.genreMovie()
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("No Data",null)
            }else{
                Resource.error("No Data",null)
            }
        }catch (e:Exception){
            Resource.error("No Data",null)
        }
    }

    override suspend fun getVideos(movieId: Int): Resource<VideosResponse> {
        return try {
            val response=mainService.getVideos(movieId)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("No Data",null)
            }else{
                Resource.error("No Data",null)
            }
        }catch (e:Exception){
            Resource.error("No Data",null)
        }
    }

    override suspend fun getNowPlayingMovies(): Resource<MoviesResponse> {
        return try {
            val response= mainService.getNowPlayingMovies()
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e: Exception){
            Resource.error("No Data",null)
        }
    }

    override suspend fun getCasting(movieId: Int): Resource<CastingResponse> {
        return try {
            val response= mainService.getCast(movieId)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e: Exception){
            Resource.error("No Data",null)
        }
    }


}