package com.backbase.assignment.movieList.network

import com.backbase.assignment.movieList.models.Movie
import org.junit.Test

class MovieEntityMapperTest {
    @Test
    fun `test movie list entity mapper`() {
        val mapper = MovieEntityMapper()

        val domain = Movie("1", 2.0, "test", "test-2", "test-3")
        val entity = MovieEntity("0", listOf(MovieItem(1L,"test-2", "test-3", 2.0, "test")))

        assert(mapper.mapFromEntity(entity)[0] == domain) {
            "Conversion failed"
        }
    }
}