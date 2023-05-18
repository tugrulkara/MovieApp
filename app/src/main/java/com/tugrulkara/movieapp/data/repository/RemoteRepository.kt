package com.tugrulkara.movieapp.data.repository

import com.tugrulkara.movieapp.data.model.CastingResponse
import com.tugrulkara.movieapp.data.model.GenresResponse
import com.tugrulkara.movieapp.data.model.MoviesResponse
import com.tugrulkara.movieapp.data.model.VideosResponse
import com.tugrulkara.movieapp.util.Resource

interface RemoteRepository {

    suspend fun getMovies(): Resource<MoviesResponse>

    suspend fun searchMovie(string: String): Resource<MoviesResponse>

    suspend fun genreMovie(): Resource<GenresResponse>

    suspend fun getVideos(movieId:Int): Resource<VideosResponse>

    suspend fun getNowPlayingMovies(): Resource<MoviesResponse>

    suspend fun getCasting(movieId:Int): Resource<CastingResponse>

}