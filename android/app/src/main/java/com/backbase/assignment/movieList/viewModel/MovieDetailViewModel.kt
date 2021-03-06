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
    init {
        onEach {
            when (it.effect) {
                is Effect.CheckMovieDetail -> {
                    repository.getDetails(it.movieId).execute(IO) { async ->
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
    }

    fun retry() {
        setState { reduce(Event.RetryTapped) }
    }

    fun setMovieId(id: String) {
        setState { reduce(Event.MovieIdEntered(id)) }
    }

    fun close() {
        setState { reduce(Event.ClosedTapped) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MovieDetailViewModel, MovieDetailState> {
        override fun create(state: MovieDetailState): MovieDetailViewModel
    }

    companion object : MavericksViewModelFactory<MovieDetailViewModel, MovieDetailState> by hiltMavericksViewModelFactory()
}