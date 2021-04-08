package com.backbase.assignment.movieList.models

import java.io.Serializable

data class MovieDetail(
        val id: Long,
        val duration: Long,
        val overview: String,
        val genres: List<String>) : Serializable







