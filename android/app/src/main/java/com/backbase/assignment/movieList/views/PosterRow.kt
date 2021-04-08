package com.backbase.assignment.movieList.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.backbase.assignment.core.loadUrl
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.PosterRowBinding
import com.backbase.assignment.movieList.models.Movie

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class PosterRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding: PosterRowBinding by viewBinding()

    @ModelProp
    fun setMovie(movie: Movie) {
        binding.imageView.loadUrl(movie.imageAsUrl)
    }

    @CallbackProp
    fun setClickListener(listener: OnClickListener?) {
        setOnClickListener(listener)
    }
}