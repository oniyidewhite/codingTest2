package com.backbase.assignment.movieList.ui

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.carousel
import com.airbnb.mvrx.*
import com.backbase.assignment.R
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.FragmentMovieListBinding
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.ui.states.MovieListState
import com.backbase.assignment.movieList.ui.states.MovieListState.*
import com.backbase.assignment.movieList.viewModel.MovieListViewModel
import com.backbase.assignment.movieList.views.PosterRowModel_
import com.backbase.assignment.movieList.views.dividerRow
import com.backbase.assignment.movieList.views.movieItemRow
import com.backbase.assignment.movieList.views.titleRow
import com.backbase.assignment.movieList.views.loadMoreRow
import com.google.android.material.snackbar.Snackbar

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MavericksView {
    private val binding: FragmentMovieListBinding by viewBinding()

    private val viewModel: MovieListViewModel by fragmentViewModel()

    private fun showMovieDetails(movie: Movie) {
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, MovieDetailFragment.arg(movie))
    }

    private fun checkStatus(state: MovieListState) {
        binding.progress.isVisible = state.showProgress
        if (state.event is Event.CheckAllFailed) {
            showSnackBarMessage()
        }
    }

    override fun invalidate(): Unit = withState(viewModel) { s ->
        when (s.event) {
            is Event.LoadedAllMovies,
            is Event.LoadedNextMostPopular -> updateUi(s.nowPlaying, s.mostPopular)
            else -> Unit
        }
        checkStatus(s)
    }

    private fun showSnackBarMessage() {
        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.label_retry) {
                fetchAllMovieTypes()
            }
        }.show()
    }

    private fun fetchAllMovieTypes() {
        viewModel.fetchAllMovieTypes()
    }

    private fun loadMoreMostPopular() {
        viewModel.fetchNextPopularList()
    }

    private fun updateUi(nowPlaying: List<Movie>?, mostPopular: List<Movie>?) {
        binding.recyclerView.withModels {
            titleRow {
                id("now-playing")
                text(R.string.label_playing_now)
            }

            val nowPlayingModels = nowPlaying?.map {
                PosterRowModel_()
                        .id("now-playing-${it.id}")
                        .movie(it)
                        .clickListener { _ -> showMovieDetails(it) }
            }

            carousel {
                id("now-playing-list")
                models(nowPlayingModels ?: emptyList())
                padding(null)
            }

            titleRow {
                id("most-popular")
                text(R.string.label_most_popular)
            }

            mostPopular?.let { pop ->
                pop.forEach {
                    movieItemRow {
                        id(it.id)
                        movie(it)
                        clickListener { _ -> showMovieDetails(it) }
                    }
                    dividerRow {
                        id("divider-${it.id}")
                    }
                }

                loadMoreRow {
                    id("progress")
                    onBind { _, _, _ ->
                        loadMoreMostPopular()
                    }
                }
            }
        }
    }
}