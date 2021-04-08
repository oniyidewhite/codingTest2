package com.backbase.assignment.core

import android.widget.ImageView
import com.backbase.assignment.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import java.text.SimpleDateFormat
import java.util.*

fun debug(action: () -> Unit) {
    if (BuildConfig.DEBUG) action()
}

private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

fun ImageView.loadUrl(string: String) {
    Glide.with(context).load(string).transition(withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
}

private val simpleDateFormat by lazy {
    SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
}

fun formatDate(value: String): String {
    val date = simpleDateFormat.parse(value)
    return date?.let { SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).format(date) } ?: "n/a"
}

fun getDurationAsString(value: Long): String {
    val hour = value / 60
    val min = value % 60

    return "${hour}h ${min}m"
}