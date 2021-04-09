package com.backbase.assignment.movieList.ui.states

import com.backbase.assignment.movieList.models.Movie
import org.junit.Before
import org.junit.Test

class MovieListStateTest {
    private lateinit var state: MovieListState

    @Before
    fun setUp() {
        state = MovieListState()
    }

    @Test
    fun `showProgress should be true and effect should be CheckAllMovies when after FindAllMoviesRequested`() {
        state = state.reduce(MovieListState.Event.FindAllMoviesRequested)
        assert(state.showProgress && state.effect == MovieListState.Effect.CheckAllMovies) {
            "$state"
        }
    }

    @Test
    fun `showProgress should be false and effect should be ShowError for network request failure`() {
        state = state.reduce(MovieListState.Event.CheckAllFailed)
        assert(!state.showProgress && state.effect == MovieListState.Effect.ShowError) {
            "$state"
        }
    }

    @Test
    fun `showProgress should be false and effect should be null for after LoadedAllMovies `() {
        state = state.reduce(MovieListState.Event.LoadedAllMovies)
        assert(!state.showProgress && state.effect == null) {
            "$state"
        }
    }

    @Test
    fun `showProgress should be false and effect should be LoadNextMostPopular for after LoadNextMostPopularRequestSent `() {
        state = state.reduce(MovieListState.Event.LoadNextMostPopularRequestSent)
        assert(!state.showProgress && state.effect == MovieListState.Effect.LoadNextMostPopular) {
            "$state"
        }
    }

    @Test
    fun `showProgress should be false and effect should be null and popularMoviesPageNo should increase by 1 after LoadedNextMostPopular`() {
        val movies = listOf(Movie("test", 2.0, "test-1", "test-2", "test-3"))
        state = state.reduce(MovieListState.Event.LoadedMostPopular(movies))
        state = state.reduce(MovieListState.Event.LoadedNextMostPopular(1, movies))
        assert(!state.showProgress && state.effect == null && state.mostPopular?.count() == 2) {
            "$state"
        }
    }
}