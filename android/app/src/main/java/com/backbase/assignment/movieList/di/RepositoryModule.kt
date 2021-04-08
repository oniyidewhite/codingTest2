package com.backbase.assignment.movieList.di

import com.backbase.assignment.core.EntityMapper
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.models.MovieDetail
import com.backbase.assignment.movieList.repository.MovieRepository
import com.backbase.assignment.movieList.network.MovieDetailEntity
import com.backbase.assignment.movieList.network.MovieEntity
import com.backbase.assignment.movieList.network.MovieWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(@Named("api_key") apiKey: String,
                          movieListMapper: EntityMapper<MovieEntity, List<Movie>>,
                          movieDetailMapper: EntityMapper<MovieDetailEntity, MovieDetail>,
                          webService: MovieWebService): MovieRepository {
        return MovieRepository(apiKey, movieListMapper, movieDetailMapper, webService)
    }
}