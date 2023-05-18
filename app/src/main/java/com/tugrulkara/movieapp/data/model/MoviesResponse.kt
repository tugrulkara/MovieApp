package com.tugrulkara.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page:Int?,
    @SerializedName("results")
    val movies:List<MoviesResult>
)
