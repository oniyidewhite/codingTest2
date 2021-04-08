package com.backbase.assignment.movieList.viewModel

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.backbase.assignment.core.di.support.AssistedViewModelFactory
import com.backbase.assignment.core.di.support.hiltMavericksViewModelFactory
import com.backbase.assignment.movieList.models.MoviePageQuery
import com.backbase.assignment.movieList.repository.MovieRepository
import com.backbase.assignment.movieList.viewModel.states.MovieListState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers.IO

class MovieListViewModel @AssistedInject constructor(
        @Assisted state: MovieListState,
        private val repository: MovieRepository) : MavericksViewModel<MovieListState>(state) {

    init {
        fetchAll()
    }

    private fun fetchNowPlaying() {
        repository.fetchNowPlaying().execute(IO) { copy(nowPlaying = it) }
    }

    fun fetchAll() {
        fetchNowPlaying()
        fetchMostPopular(1)
    }

    fun fetchMostPopular(pageNo: Int) {
        repository.fetchMostPopular(MoviePageQuery(pageNo)).execute(IO) {
            copy(mostPopular = it)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MovieListViewModel, MovieListState> {
        override fun create(state: MovieListState): MovieListViewModel
    }

    companion object : MavericksViewModelFactory<MovieListViewModel, MovieListState> by hiltMavericksViewModelFactory()
}