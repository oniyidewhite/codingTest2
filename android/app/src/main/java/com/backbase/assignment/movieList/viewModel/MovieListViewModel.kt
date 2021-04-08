package com.backbase.assignment.movieList.viewModel

import com.airbnb.mvrx.*
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
        repository.fetchNowPlaying().execute(IO) {
            val list = it()
            val status = if (list != null && mostPopular != null) {
                Success(Unit)
            } else {
                loading
            }
            copy(nowPlaying = list, loading = status)
        }
    }

    fun fetchAll() {
        setState { copy(loading = Loading(), popularMoviesNextPageNo = 1) }
        fetchNowPlaying()
        fetchMostPopular()
    }

    private fun fetchMostPopular(): Unit = withState {
        repository.fetchMostPopular(MoviePageQuery(it.popularMoviesNextPageNo)).execute(IO) {
            if (mostPopular == null) {
                val list = it()
                val status = if (list != null && nowPlaying != null) {
                    Success(Unit)
                } else {
                    loading
                }

                copy(mostPopular = list, loading = status)
            } else {
                val list = it()
                val (content, status) = if (list != null) {
                    Pair(mostPopular.toMutableList().apply { addAll(list) }, Success(Unit) as Async<Unit>)
                } else {
                    Pair(mostPopular, Loading())
                }

                copy(mostPopular = content, loading = status)
            }
        }
    }

    fun fetchPopularNextPage() {
        setState { copy(loading = Loading(), popularMoviesNextPageNo = popularMoviesNextPageNo + 1) }
        fetchMostPopular()
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MovieListViewModel, MovieListState> {
        override fun create(state: MovieListState): MovieListViewModel
    }

    companion object : MavericksViewModelFactory<MovieListViewModel, MovieListState> by hiltMavericksViewModelFactory()
}