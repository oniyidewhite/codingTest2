package com.backbase.assignment.movieList.models

import java.io.Serializable

data class Movie(
        val id: String,
        val rating: Double,
        val image: String,
        val title: String,
        val releaseDate: String,
) : Serializable {
    val imageAsUrl: String
        get() = "$imageFullPathReduced$image"

    val originalImageAsUrl: String
        get() = "$imageFullPathOriginal$image"

    private companion object {
        const val imageFullPathReduced = "https://image.tmdb.org/t/p/w500/"
        const val imageFullPathOriginal = "https://image.tmdb.org/t/p/original/"
    }
}