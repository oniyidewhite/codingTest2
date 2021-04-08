package com.backbase.assignment.movieList

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
import com.backbase.assignment.movieList.viewModel.states.MovieDetailState
import com.backbase.assignment.movieList.viewModel.MovieDetailViewModel
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
        loadDetails()
        binding.backImage.setOnClickListener { closeDetail() }
    }

    private fun checkStatus(state: MovieDetailState) {
        binding.progress.isVisible = state.movieDetail is Loading
        if (state.movieDetail is Fail) {
            showSnackBarMessage()
        }
    }

    private fun loadDetails() {
        viewModel.getDetails(movie)
    }

    private fun showSnackBarMessage() {
        Snackbar.make(binding.root, getString(R.string.error_message), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.label_retry) {
                loadDetails()
            }
        }.show()
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.recyclerView.withModels {

            state.movieDetail()?.let { data ->
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
        checkStatus(state)
    }

    companion object {
        fun arg(movie: Movie) = Bundle().apply {
            putSerializable(Mavericks.KEY_ARG, movie)
        }
    }
}