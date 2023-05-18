package com.tugrulkara.movieapp.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([MovieEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase(){

    abstract fun dao():MoviesDAO

}