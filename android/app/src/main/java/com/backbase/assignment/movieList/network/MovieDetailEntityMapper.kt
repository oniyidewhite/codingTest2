package com.backbase.assignment.movieList.network

import com.backbase.assignment.core.EntityMapper
import com.backbase.assignment.movieList.models.MovieDetail
import javax.inject.Inject

class MovieDetailEntityMapper @Inject constructor() : EntityMapper<MovieDetailEntity, MovieDetail> {
    override fun mapFromEntity(entity: MovieDetailEntity): MovieDetail {
        return MovieDetail(id = entity.id, duration = entity.runtime, overview = entity.overview, genres = entity.genres.map { it.name })
    }
}