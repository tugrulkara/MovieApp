package com.tugrulkara.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.tugrulkara.movieapp.data.roomdb.MovieEntity

interface LocalRepository {

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun deleteMovie(id: Int)

    fun getMovies(): LiveData<List<MovieEntity>>

    fun getFavMovie(id:Int): LiveData<MovieEntity>

}