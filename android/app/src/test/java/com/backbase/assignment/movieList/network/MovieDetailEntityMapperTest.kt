package com.backbase.assignment.movieList.network

import com.backbase.assignment.movieList.models.MovieDetail
import org.junit.Test

class MovieDetailEntityMapperTest {
    @Test
    fun `test movie detail entity mapper`() {
        val mapper = MovieDetailEntityMapper()

        val domain = MovieDetail(1L, 2L, "overview", listOf("test"))
        val entity = MovieDetailEntity(1L, 2L, "overview", listOf(Genre("test")))

        assert(mapper.mapFromEntity(entity) == domain) {
            "Conversion failed"
        }
    }
}