package com.backbase.assignment.movieList.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import com.backbase.assignment.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class DividerRow @JvmOverloads constructor(
        context: Context,
        attribSet: AttributeSet? = null,
        defStyleAttr: Int = 0) : FrameLayout(context, attribSet, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.divider_row, this, true)
    }
}