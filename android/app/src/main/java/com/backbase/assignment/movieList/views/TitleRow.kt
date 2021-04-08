package com.backbase.assignment.movieList.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.backbase.assignment.core.mavericks.viewBinding
import com.backbase.assignment.databinding.TitleRowBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TitleRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding: TitleRowBinding by viewBinding()

    @TextProp
    @JvmOverloads
    fun setText(title: CharSequence = "") {
        binding.textView.text = title
    }
}