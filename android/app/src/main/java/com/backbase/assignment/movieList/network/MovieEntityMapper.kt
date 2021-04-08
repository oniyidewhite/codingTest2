package com.backbase.assignment.movieList.network

import com.backbase.assignment.core.EntityMapper
import com.backbase.assignment.movieList.models.Movie
import javax.inject.Inject

class MovieEntityMapper @Inject constructor() : EntityMapper<MovieEntity, List<Movie>> {
    override fun mapFromEntity(entity: MovieEntity): List<Movie> {
        return entity.movies.map {
            Movie(id = it.id.toString(), rating = it.rating, image = it.image, title = it.title, releaseDate = it.releaseDate ?: "")
        }
    }
}