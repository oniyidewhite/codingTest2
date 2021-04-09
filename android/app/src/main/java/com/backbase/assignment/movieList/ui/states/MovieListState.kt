package com.backbase.assignment.movieList.ui.states

import com.airbnb.mvrx.MavericksState
import com.backbase.assignment.movieList.models.Movie

data class MovieListState(
        private val inProgress: Boolean = true,
        val nowPlaying: List<Movie>? = null,
        val mostPopular: List<Movie>? = null,
        val popularMoviesPageNo: Int = 0,
        val effect: Effect? = null,
        val event: Event? = null) : MavericksState {
    val showProgress get() = inProgress

    fun reduce(e: Event): MovieListState {
        return when (e) {
            Event.FindAllMoviesRequested -> copy(event = e, inProgress = true, effect = Effect.CheckAllMovies, popularMoviesPageNo = 0)
            is Event.LoadedMostPopular -> mergeLoaded(e, e.movies)
            is Event.LoadedNowPlaying -> mergeLoaded(e, e.movies)
            Event.CheckAllFailed -> copy(event = e, effect = Effect.ShowError, inProgress = false)
            Event.LoadedAllMovies -> copy(event = e, effect = null, inProgress = false)

            Event.HandledEffect -> copy(event = e, effect = null)

            Event.NextMostPopularFailed -> copy(event = e, effect = null)
            Event.LoadNextMostPopularRequestSent -> copy(event = e, effect = Effect.LoadNextMostPopular, inProgress = false)
            is Event.LoadedNextMostPopular -> copy(event = e, inProgress = false, popularMoviesPageNo = e.pageNo + 1, effect = null, mostPopular = mostPopular?.toMutableList()?.apply { addAll(e.movies) }
                    ?: error("should not happen"))

            is Event.MovieDetailTapped -> copy(event = e, effect = Effect.MovieDetail(e.movie))
        }
    }

    // this handles only `LoadedMostPopular` and `LoadedNowPlaying`, converts them to `LoadedAllMovies` if all is good
    // Ui only listens for `LoadedAllMovies`
    private fun mergeLoaded(event: Event, movies: List<Movie>): MovieListState {
        return if (event is Event.LoadedNowPlaying && !mostPopular.isNullOrEmpty()) {
            copy(event = Event.LoadedAllMovies, nowPlaying = movies, effect = null, inProgress = false)
        } else if (event is Event.LoadedMostPopular && !nowPlaying.isNullOrEmpty()) {
            copy(event = Event.LoadedAllMovies, mostPopular = movies, effect = null, inProgress = false, popularMoviesPageNo = 1)
        } else if (event is Event.LoadedMostPopular) {
            copy(event = event, mostPopular = movies, effect = null, popularMoviesPageNo = 1)
        } else if (event is Event.LoadedNowPlaying) {
            copy(event = event, nowPlaying = movies, effect = null)
        } else {
            error("should not happen")
        }
    }

    sealed class Event {
        object FindAllMoviesRequested : Event()
        data class LoadedMostPopular(val movies: List<Movie>) : Event()
        data class LoadedNowPlaying(val movies: List<Movie>) : Event()
        object LoadedAllMovies : Event()
        object CheckAllFailed : Event()

        object HandledEffect : Event()

        object LoadNextMostPopularRequestSent : Event()
        data class LoadedNextMostPopular(val pageNo: Int, val movies: List<Movie>) : Event()
        object NextMostPopularFailed : Event()

        data class MovieDetailTapped(val movie: Movie) : Event()
    }

    sealed class Effect {
        object CheckAllMovies : Effect()
        object LoadNextMostPopular : Effect()
        data class MovieDetail(val movie: Movie) : Effect()
        object ShowError : Effect()
    }
}