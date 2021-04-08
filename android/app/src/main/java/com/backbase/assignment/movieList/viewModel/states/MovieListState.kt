package com.backbase.assignment.movieList.viewModel.states

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.PersistState
import com.airbnb.mvrx.Uninitialized
import com.backbase.assignment.movieList.models.Movie
import java.io.Serializable

data class MovieListState(
        val nowPlaying: Async<List<Movie>> = Uninitialized,
        val mostPopular: Async<List<Movie>> = Uninitialized) : MavericksState