package com.backbase.assignment.movieList.ui.states

import com.airbnb.mvrx.MavericksState
import com.backbase.assignment.movieList.models.MovieDetail

data class MovieDetailState(
        val movieId: String = "",
        private val inProgress: Boolean = true,
        val movieDetail: MovieDetail? = null,
        val event: Event? = null,
        val effect: Effect? = null,
) : MavericksState {
    val showProgress get() = inProgress

    fun reduce(e: Event): MovieDetailState {
        return when (e) {
            is Event.LoadRequestFailed -> copy(event = e, inProgress = false, effect = Effect.ShowError)
            is Event.LoadedMovieDetail -> copy(event = e, movieDetail = e.movieDetail, effect = null, inProgress = false)
            is Event.MovieIdEntered -> copy(event = e, movieId = e.id, effect = Effect.CheckMovieDetail)
            is Event.LoadRequestSent -> copy(event = e, inProgress = true, effect = null)
            is Event.RetryTapped -> copy(event = e, effect = Effect.CheckMovieDetail, inProgress = false)
            is Event.ClosedTapped -> copy(event = e, effect = Effect.Close)
        }
    }

    sealed class Event {
        data class MovieIdEntered(val id: String) : Event()
        data class LoadedMovieDetail(val movieDetail: MovieDetail) : Event()
        object RetryTapped : Event()
        object LoadRequestFailed : Event()
        object LoadRequestSent : Event()
        object ClosedTapped : Event()
    }

    sealed class Effect {
        // Out: UI Events
        object CheckMovieDetail : Effect()
        object Close : Effect()
        object ShowError : Effect()
    }
}
