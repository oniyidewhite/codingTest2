package com.backbase.assignment.movieList.viewModel

import com.airbnb.mvrx.*
import com.backbase.assignment.core.di.support.AssistedViewModelFactory
import com.backbase.assignment.core.di.support.hiltMavericksViewModelFactory
import com.backbase.assignment.movieList.repository.MovieRepository
import com.backbase.assignment.movieList.ui.states.MovieDetailState.*
import com.backbase.assignment.movieList.ui.states.MovieDetailState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers.IO

class MovieDetailViewModel @AssistedInject constructor(
        @Assisted state: MovieDetailState,
        private val repository: MovieRepository) : MavericksViewModel<MovieDetailState>(state) {

    fun retry() {
        setState { reduce(Event.RetryTapped) }
        checkState()
    }

    fun setMovieId(id: String) {
        setState { reduce(Event.MovieIdEntered(id)) }
        checkState()
    }

    private fun checkState() = withState { s ->
        when (s.effect) {
            is Effect.CheckMovieDetail -> {
                repository.getDetails(s.movieId).execute(IO) { async ->
                    when (async) {
                        is Loading -> {
                            reduce(Event.LoadRequestSent)
                        }
                        is Fail -> {
                            reduce(Event.LoadRequestFailed)
                        }
                        is Success -> {
                            reduce(Event.LoadedMovieDetail(async()))
                        }
                        else -> this
                    }
                }
            }
            else -> Unit
        }
    }

    fun closeDetail() {
        setState { reduce(Event.ClosedTapped) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MovieDetailViewModel, MovieDetailState> {
        override fun create(state: MovieDetailState): MovieDetailViewModel
    }

    companion object : MavericksViewModelFactory<MovieDetailViewModel, MovieDetailState> by hiltMavericksViewModelFactory()
}