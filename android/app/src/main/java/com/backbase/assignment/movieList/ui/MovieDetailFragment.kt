package com.backbase.assignment.movieList.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.carousel
import com.airbnb.mvrx.*
import com.backbase.assignment.R
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.FragmentMovieDetailBinding
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.models.MovieDetail
import com.backbase.assignment.movieList.viewModel.MovieDetailViewModel
import com.backbase.assignment.movieList.ui.states.MovieDetailState.*
import com.backbase.assignment.movieList.views.TagsRowModel_
import com.backbase.assignment.movieList.views.overviewRow
import com.backbase.assignment.movieList.views.posterTitleRow
import com.google.android.material.snackbar.Snackbar

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail), MavericksView {
    private val binding: FragmentMovieDetailBinding by viewBinding()

    private val viewModel: MovieDetailViewModel by fragmentViewModel()
    private val movie: Movie by args()

    private fun closeDetail() {
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backImage.setOnClickListener { viewModel.closeDetail() }
        viewModel.setMovieId(movie.id)
    }

    private fun retry() {
        viewModel.retry()
    }

    private fun showSnackBarMessage() {
        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.label_retry) {
                retry()
            }
        }.show()
    }

    private fun updateUi(data: MovieDetail) {
        binding.recyclerView.withModels {
            posterTitleRow {
                id("poster-${data.id}")

                movie(movie)
                duration(data.duration)
            }

            overviewRow {
                id("overview-${data.id}")
                content(data.overview)
            }

            val tags = data.genres.mapIndexed { k, v ->
                TagsRowModel_()
                        .id("tag-${k}")
                        .details(v)
            }

            carousel {
                id("carousel-tag-${data.id}")
                models(tags)
            }
        }
    }

    private fun stateEffect(effect: Effect?) {
        when (effect) {
            Effect.Close -> closeDetail()
            is Effect.ShowError -> showSnackBarMessage()
            else -> Unit
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        when (val e = state.event) {
            is Event.LoadedMovieDetail -> updateUi(e.movieDetail)
            else -> Unit
        }

        stateEffect(state.effect)
        binding.progress.isVisible = state.showProgress
    }

    companion object {
        fun arg(movie: Movie) = Bundle().apply {
            putSerializable(Mavericks.KEY_ARG, movie)
        }
    }
}