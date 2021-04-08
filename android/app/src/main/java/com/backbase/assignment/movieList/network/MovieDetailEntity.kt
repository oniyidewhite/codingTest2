package com.backbase.assignment.movieList.network

import com.google.gson.annotations.SerializedName

data class MovieDetailEntity(
        @SerializedName("id")
        val id: Long,
        @SerializedName("runtime")
        val runtime: Long,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("genres")
        val genres: List<Genre>,
)

data class Genre(
        @SerializedName("name") val name: String
)