package com.backbase.assignment.movieList

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.carousel
import com.airbnb.mvrx.*
import com.backbase.assignment.R
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.FragmentMovieListBinding
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.viewModel.states.MovieListState
import com.backbase.assignment.movieList.viewModel.MovieListViewModel
import com.backbase.assignment.movieList.views.PosterRowModel_
import com.backbase.assignment.movieList.views.dividerRow
import com.backbase.assignment.movieList.views.movieItemRow
import com.backbase.assignment.movieList.views.titleRow
import com.google.android.material.snackbar.Snackbar

class MovieListFragment : Fragment(R.layout.fragment_movie_list), MavericksView {
    private val binding: FragmentMovieListBinding by viewBinding()

    private val viewModel: MovieListViewModel by fragmentViewModel()

    private fun showMovieDetails(movie: Movie) {
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailFragment, MovieDetailFragment.arg(movie))
    }

    private fun showSnackBarMessage() {
        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.label_retry) {
                viewModel.fetchAll()
            }
        }.show()
    }

    private fun checkStatus(state: MovieListState) {
        binding.progress.isVisible = state.mostPopular is Loading || state.nowPlaying is Loading
        if (state.nowPlaying is Fail || state.mostPopular is Fail) {
            showSnackBarMessage()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.recyclerView.withModels {
            titleRow {
                id("now-playing")
                text(R.string.label_playing_now)
            }

            val nowPlayingModels = state.nowPlaying()?.map {
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

            state.mostPopular()?.forEach {
                movieItemRow {
                    id(it.id)
                    movie(it)
                    clickListener { _ -> showMovieDetails(it) }
                }
                dividerRow {
                    id("divider-${it.id}")
                }
            }

            checkStatus(state)
        }
    }
}