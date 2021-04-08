package com.backbase.assignment.movieList.repository

import com.backbase.assignment.core.EntityMapper
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.models.MovieDetail
import com.backbase.assignment.movieList.models.MoviePageQuery
import com.backbase.assignment.movieList.network.MovieDetailEntity
import com.backbase.assignment.movieList.network.MovieEntity
import com.backbase.assignment.movieList.network.MovieWebService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(private val apiKey: String,
                      private val movieListMapper: EntityMapper<MovieEntity, List<Movie>>,
                      private val movieDetailMapper: EntityMapper<MovieDetailEntity, MovieDetail>,
                      private val webService: MovieWebService) {
    fun fetchNowPlaying(): Flow<List<Movie>> = flow {
        emit(movieListMapper.mapFromEntity(webService.nowPlaying(key = apiKey)))
    }

    fun fetchMostPopular(value: MoviePageQuery): Flow<List<Movie>> = flow {
        try {
            emit(movieListMapper.mapFromEntity(webService.popular(page = value.page, key = apiKey)))
        } catch (ex: Throwable) {
            ex.printStackTrace()
            throw ex
        }
    }

    fun getDetails(value: Movie): Flow<MovieDetail> = flow {
        emit(movieDetailMapper.mapFromEntity(webService.details(id = value.id, key = apiKey)))
    }
}