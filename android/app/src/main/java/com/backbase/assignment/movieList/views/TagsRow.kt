package com.backbase.assignment.movieList.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.TagsRowBinding

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class TagsRow @JvmOverloads constructor(
        context: Context,
        attribSet: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attribSet, defStyleAttr) {
    private val binding: TagsRowBinding by viewBinding()

    @ModelProp
    fun setDetails(tag: String) {
        binding.tagText.text = tag
    }
}