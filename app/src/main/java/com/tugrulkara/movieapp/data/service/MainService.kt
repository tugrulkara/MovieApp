package com.tugrulkara.movieapp.data.service

import com.tugrulkara.movieapp.data.model.CastingResponse
import com.tugrulkara.movieapp.data.model.GenresResponse
import com.tugrulkara.movieapp.data.model.MoviesResponse
import com.tugrulkara.movieapp.data.model.VideosResponse
import com.tugrulkara.movieapp.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {

    @GET("/3/trending/movie/day")
    suspend fun getMovies(
        @Query("api_key") apiKey: String=API_KEY
    ): Response<MoviesResponse>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String= API_KEY
    ):Response<MoviesResponse>

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query") query:String?,
        @Query("api_key") apiKey: String=API_KEY
    ): Response<MoviesResponse>

    @GET("/3/genre/movie/list")
    suspend fun genreMovie(
        @Query("api_key") apiKey: String=API_KEY
    ): Response<GenresResponse>

    @GET("/3/movie/{movieId}/videos")
    suspend fun getVideos(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String=API_KEY
    ): Response<VideosResponse>

    @GET("/3/movie/{movieId}/credits")
    suspend fun getCast(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String=API_KEY
    ):Response<CastingResponse>
}