package com.tugrulkara.movieapp.data.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movies")
data class MovieEntity(

    val backdropPath:String?,
    val id:Int?,
    val title:String?,
    val originalLanguage:String?,
    val originalTitle: String?,
    val overview:String?,
    var genreIds: String,
    val posterPath:String?,
    val popularity:Double?,
    val releaseDate:String?,
    val video:Boolean?,
    val voteAverage:Double?,
    val voteCount:Int?
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}
