package com.tugrulkara.movieapp.util

import com.tugrulkara.movieapp.data.model.MoviesResult
import com.tugrulkara.movieapp.data.roomdb.MovieEntity

object DataMapper {

    fun mapMovieResultToMovieEntity(input: MoviesResult): MovieEntity {
        return MovieEntity(
            backdropPath = input.backdropPath,
            id = input.id,
            title = input.title,
            originalLanguage = input.originalLanguage,
            originalTitle= input.originalTitle,
            overview = input.overview,
            genreIds = Converters.fromList(input.genreIds),
            posterPath = input.posterPath,
            popularity = input.popularity,
            releaseDate = input.releaseDate,
            video=input.video,
            voteAverage = input.voteAverage,
            voteCount=input.voteCount
        )
    }

    fun mapMovieEntityToMovieResult(input: MovieEntity): MoviesResult {
        return MoviesResult(
            adult = null,
            backdropPath = input.backdropPath,
            id = input.id,
            title = input.title,
            originalLanguage = input.originalLanguage,
            originalTitle = null,
            overview = input.overview,
            posterPath = input.posterPath,
            //mediaType=null,
            genreIds = Converters.fromString(input.genreIds),
            popularity = input.popularity,
            releaseDate = input.releaseDate,
            video = null,
            voteAverage = input.voteAverage,
            voteCount = null
        )
    }

}