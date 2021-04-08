package com.backbase.assignment.movieList.network

import com.google.gson.annotations.SerializedName

data class MovieEntity(
        @SerializedName("page")
        val pageNo: String,
        @SerializedName("results")
        val movies: List<MovieItem>
)

data class MovieItem(
        @SerializedName("id")
        val id: Long,
        @SerializedName("original_title")
        val title: String,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("vote_average")
        val rating: Double,
        @SerializedName("poster_path")
        val image: String
)