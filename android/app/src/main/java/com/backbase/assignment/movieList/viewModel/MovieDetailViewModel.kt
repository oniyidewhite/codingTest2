package com.backbase.assignment.movieList.viewModel

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.backbase.assignment.core.di.support.AssistedViewModelFactory
import com.backbase.assignment.core.di.support.hiltMavericksViewModelFactory
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.repository.MovieRepository
import com.backbase.assignment.movieList.viewModel.states.MovieDetailState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MovieDetailViewModel @AssistedInject constructor(
        @Assisted state: MovieDetailState,
        private val repository: MovieRepository) : MavericksViewModel<MovieDetailState>(state) {

    fun getDetails(movie: Movie) {
        repository.getDetails(movie).execute { copy(movieDetail = it) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MovieDetailViewModel, MovieDetailState> {
        override fun create(state: MovieDetailState): MovieDetailViewModel
    }

    companion object : MavericksViewModelFactory<MovieDetailViewModel, MovieDetailState> by hiltMavericksViewModelFactory()
}