package com.backbase.assignment.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.backbase.assignment.R
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.ViewRatingBinding

class RatingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding: ViewRatingBinding by viewBinding()

    fun setRating(value: Double) {
        val percentage = (value * 10).toInt()
        binding.indicatorView.max = 100
        binding.indicatorView.progress = percentage
        binding.indicatorView.isIndeterminate = false
        binding.valueView.text = "$percentage"

        if (percentage >= 50) {
            // paint green
            binding.indicatorView.background.setTint(ContextCompat.getColor(context, R.color.colorDarkGreen))
            binding.indicatorView.progressDrawable.setTint(ContextCompat.getColor(context, R.color.colorLightGreen))
        } else {
            // paint yellow
            binding.indicatorView.background.setTint(ContextCompat.getColor(context, R.color.colorDarkYellow))
            binding.indicatorView.progressDrawable.setTint(ContextCompat.getColor(context, R.color.colorLightYellow))
        }
    }
}