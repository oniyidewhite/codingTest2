package com.backbase.assignment.movieList.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.backbase.assignment.core.formatDate
import com.backbase.assignment.core.loadUrl
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.PopularRowBinding
import com.backbase.assignment.movieList.models.Movie

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MovieItemRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding: PopularRowBinding by viewBinding()

    @ModelProp
    fun setMovie(movie: Movie): Unit = with(binding) {
        imageView.loadUrl(movie.imageAsUrl)
        ratingView.setRating(movie.rating)

        titleText.text = movie.title
        releaseDateText.text = formatDate(movie.releaseDate)
        durationText.text = "n/a"

    }

    @CallbackProp
    fun setClickListener(listener: OnClickListener?) {
        setOnClickListener(listener)
    }
}