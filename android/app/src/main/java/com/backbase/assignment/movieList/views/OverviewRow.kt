package com.backbase.assignment.movieList.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.OverviewRowBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class OverviewRow @JvmOverloads constructor(
        context: Context,
        attribSet: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attribSet, defStyleAttr) {
    private val binding: OverviewRowBinding by viewBinding()

    @ModelProp
    fun content(content: String) {
        binding.contentText.text = content
    }
}