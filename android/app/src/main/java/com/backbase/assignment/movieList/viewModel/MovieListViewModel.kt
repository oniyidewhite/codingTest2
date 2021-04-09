package com.backbase.assignment.movieList.viewModel

import com.airbnb.mvrx.*
import com.backbase.assignment.core.di.support.AssistedViewModelFactory
import com.backbase.assignment.core.di.support.hiltMavericksViewModelFactory
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.models.MoviePageQuery
import com.backbase.assignment.movieList.repository.MovieRepository
import com.backbase.assignment.movieList.ui.states.MovieListState
import com.backbase.assignment.movieList.ui.states.MovieListState.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers.IO

class MovieListViewModel @AssistedInject constructor(
        @Assisted state: MovieListState,
        private val repository: MovieRepository) : MavericksViewModel<MovieListState>(state) {
    init {
        onEach(MovieListState::effect) {
            when (it) {
                Effect.CheckAllMovies -> {
                    fetchNowPlaying()
                    fetchMostPopular()
                }
                Effect.LoadNextMostPopular -> fetchMostPopular()
                else -> Unit
            }
        }
        fetchAllMovieTypes()
    }

    fun fetchAllMovieTypes() {
        setState { reduce(Event.FindAllMoviesRequested) }
    }

    fun fetchNextPopularList() {
        setState { reduce(Event.LoadNextMostPopularRequestSent) }
    }

    fun selectMovie(movie: Movie) {
        setState { reduce(Event.MovieDetailTapped(movie)) }
    }

    private fun fetchNowPlaying() {
        repository.fetchNowPlaying().execute(IO) { async ->
            when (async) {
                is Fail -> {
                    reduce(Event.CheckAllFailed)
                }
                is Success -> {
                    reduce(Event.LoadedNowPlaying(async()))
                }
                else -> this
            }
        }
    }

    private fun fetchMostPopular() = withState {
        repository.fetchMostPopular(MoviePageQuery(page = it.popularMoviesPageNo + 1)).execute(IO) { async ->
            when (async) {
                is Fail -> {
                    if (it.popularMoviesPageNo == 1) {
                        reduce(Event.CheckAllFailed)
                    } else {
                        reduce(Event.NextMostPopularFailed)
                    }
                }
                is Success -> {
                    if (it.popularMoviesPageNo == 0) {
                        reduce(Event.LoadedMostPopular(async()))
                    } else {
                        reduce(Event.LoadedNextMostPopular(it.popularMoviesPageNo, async()))
                    }
                }
                else -> this
            }
        }
    }

    fun handledEffect() {
        setState { reduce(Event.HandledEffect) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MovieListViewModel, MovieListState> {
        override fun create(state: MovieListState): MovieListViewModel
    }

    companion object : MavericksViewModelFactory<MovieListViewModel, MovieListState> by hiltMavericksViewModelFactory()
}