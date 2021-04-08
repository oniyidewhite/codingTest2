package com.backbase.assignment.movieList.viewModel.states

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.backbase.assignment.movieList.models.Movie

data class MovieListState(
        val nowPlaying: List<Movie>? = null,
        val mostPopular: List<Movie>? = null,
        val loading: Async<Unit> = Uninitialized) : MavericksState //Async<List<Movie>> = Uninitialized