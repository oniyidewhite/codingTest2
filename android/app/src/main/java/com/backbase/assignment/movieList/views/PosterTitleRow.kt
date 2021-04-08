package com.backbase.assignment.movieList.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.backbase.assignment.core.formatDate
import com.backbase.assignment.core.getDurationAsString
import com.backbase.assignment.core.loadUrl
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.PosterTitleRowBinding
import com.backbase.assignment.movieList.models.Movie
import com.backbase.assignment.movieList.models.MovieDetail

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class PosterTitleRow @JvmOverloads constructor(
        context: Context,
        attribSet: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attribSet, defStyleAttr) {
    private val binding: PosterTitleRowBinding by viewBinding()

    @ModelProp
    fun setMovie(movie: Movie) {
        binding.releaseDateText.text = formatDate(movie.releaseDate)
        binding.titleText.text = movie.title
        binding.imageView.loadUrl(movie.originalImageAsUrl)
    }

    @ModelProp
    fun setDuration(value: Long) {
        binding.durationText.text = "- ${getDurationAsString(value)}"
    }

    companion object {

    }
}