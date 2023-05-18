package com.tugrulkara.movieapp.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM movies WHERE id =:id")
    suspend fun deleteMovie(id: Int)

    @Query("SELECT * FROM movies")
    fun getMovies() : LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id =:id")
    fun getFavMovie(id: Int): LiveData<MovieEntity>


}