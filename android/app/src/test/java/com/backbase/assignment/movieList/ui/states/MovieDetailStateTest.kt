package com.backbase.assignment.movieList.ui.states

import com.backbase.assignment.movieList.models.MovieDetail
import org.junit.Before
import org.junit.Test

class MovieDetailStateTest {
    private lateinit var state: MovieDetailState

    @Before
    fun setUp() {
        state = MovieDetailState()
    }

    @Test
    fun `showProgress should be true when network request is sent`() {
        state = state.reduce(MovieDetailState.Event.MovieIdEntered("test"))
        assert(state.effect == MovieDetailState.Effect.CheckMovieDetail && state.movieId == "test" && !state.showProgress) {
            "$state"
        }

        state = state.reduce(MovieDetailState.Event.LoadRequestSent)
        assert(state.effect == null && state.showProgress) {
            "$state"
        }
    }

    @Test
    fun `show progress should be false and effect should be showError when there is a problem loading the network resource`() {
        state = state.reduce(MovieDetailState.Event.LoadRequestSent)
        assert(state.effect == null && state.showProgress) {
            "$state"
        }

        state = state.reduce(MovieDetailState.Event.LoadRequestFailed)
        assert(state.effect == MovieDetailState.Effect.ShowError && !state.showProgress) {
            "$state"
        }
    }

    @Test
    fun `Effect should be CheckMovieDetail when retry is called`() {
        state = state.reduce(MovieDetailState.Event.LoadRequestSent)
        assert(state.effect == null && state.showProgress) {
            "$state"
        }

        state = state.reduce(MovieDetailState.Event.LoadRequestFailed)
        assert(state.effect == MovieDetailState.Effect.ShowError && !state.showProgress) {
            "$state"
        }

        state = state.reduce(MovieDetailState.Event.RetryTapped)
        assert(state.effect == MovieDetailState.Effect.CheckMovieDetail && !state.showProgress) {
            "$state"
        }

        state = state.reduce(MovieDetailState.Event.LoadRequestSent)
        assert(state.effect == null && state.showProgress) {
            "$state"
        }
    }

    @Test
    fun `We should have movieDetail obj and showProgress should be false once LoadedMovieDetail is called`() {
        state = state.reduce(MovieDetailState.Event.LoadedMovieDetail(MovieDetail(1L, 2L, "test", emptyList())))
        assert(state.effect == null && !state.showProgress && state.movieDetail?.id == 1L) {
            "$state"
        }
    }
}