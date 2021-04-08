package com.backbase.assignment.movieList.viewModel.states

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.backbase.assignment.movieList.models.MovieDetail

data class MovieDetailState(val movieDetail: Async<MovieDetail> = Uninitialized) : MavericksState