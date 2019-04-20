package com.adammcneilly.pokedex

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

private const val PROGRESS_STROKE_WIDTH = 10F
private const val PROGRESS_CENTER_RADIUS = 30F

@BindingAdapter("visibilityCondition")
fun View.visibleIf(condition: Boolean?) {
    this.visibility = if (condition == true) View.VISIBLE else View.GONE
}

@BindingAdapter("toolbarColorRes")
fun Toolbar.colorRes(colorRes: Int?) {
    colorRes?.let(this::setBackgroundResource)
}

@BindingAdapter("textColorRes")
fun Toolbar.textColorRes(colorRes: Int?) {
    colorRes?.let {
        setTitleTextColor(ContextCompat.getColor(context, colorRes))
    }
}

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(imageUrl: String) {
    val progressColor = ContextCompat.getColor(context, R.color.colorAccent)

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = PROGRESS_STROKE_WIDTH
    circularProgressDrawable.centerRadius = PROGRESS_CENTER_RADIUS
    circularProgressDrawable.setColorFilter(progressColor, PorterDuff.Mode.SRC_IN)
    circularProgressDrawable.start()

    Glide.with(this)
        .load(imageUrl)
        .placeholder(circularProgressDrawable)
        .into(this)
}