package com.backbase.assignment.movieList.repository

import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.models.MoviePageQuery
import com.backbase.assignment.movieList.network.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {
    private val apiKey = "test"
    private lateinit var movieDetailEntityMapper: MovieDetailEntityMapper
    private lateinit var movieEntityMapper: MovieEntityMapper
    private lateinit var webService: MovieWebService

    @Before
    fun setUp() {
        movieDetailEntityMapper = MovieDetailEntityMapper()
        movieEntityMapper = MovieEntityMapper()
        webService = mock()
    }

    @Test
    fun `test repository fetch popular movies`(): Unit = runBlocking {
        val entity = MovieEntity("0", listOf(MovieItem(1L, "test-2", "test-3", 2.0, "test")))

        whenever(webService.popular(1, apiKey)).thenReturn(entity)
        val repository = MovieRepository(apiKey, movieEntityMapper, movieDetailEntityMapper, webService)

        repository.fetchMostPopular(MoviePageQuery(1)).collect {
            assert(it[0].id == entity.movies[0].id.toString()) {
                "Invalid response"
            }
        }
    }

    @Test
    fun `test repository movie details`(): Unit = runBlocking {
        val entity = MovieDetailEntity(1L, 2L, "overview", listOf(Genre("test")))
        val movie = Movie("1", 2.0, "test", "test-2", "test-3")

        whenever(webService.details("1", apiKey)).thenReturn(entity)

        val repository = MovieRepository(apiKey, movieEntityMapper, movieDetailEntityMapper, webService)

        repository.getDetails(movie.id).collect {
            assert(it.id == entity.id) {
                "Invalid response"
            }
        }
    }

    @Test
    fun `test repository fetch now playing`(): Unit = runBlocking {
        val entity = MovieEntity("1", listOf(MovieItem(1L, "test-2", "test-3", 2.0, "test")))

        whenever(webService.nowPlaying(apiKey)).thenReturn(entity)
        val repository = MovieRepository(apiKey, movieEntityMapper, movieDetailEntityMapper, webService)

        repository.fetchNowPlaying().collect {
            println(it[0].id)
            println(entity.movies[0].id.toString())
            assert(it[0].id == entity.movies[0].id.toString()) {
                "Invalid response"
            }
        }
    }
}