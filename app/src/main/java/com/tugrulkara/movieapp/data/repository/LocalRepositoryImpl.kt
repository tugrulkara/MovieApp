package com.tugrulkara.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.tugrulkara.movieapp.data.roomdb.MovieEntity
import com.tugrulkara.movieapp.data.roomdb.MoviesDAO
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(private val moviesDAO: MoviesDAO):LocalRepository {

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        moviesDAO.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(id: Int) {
        moviesDAO.deleteMovie(id)
    }


    override fun getMovies(): LiveData<List<MovieEntity>> {
        return moviesDAO.getMovies()
    }

    override fun getFavMovie(id: Int): LiveData<MovieEntity> {
        return moviesDAO.getFavMovie(id)
    }

}