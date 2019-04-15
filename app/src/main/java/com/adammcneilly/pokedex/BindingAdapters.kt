package com.adammcneilly.pokedex

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

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
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN)
    circularProgressDrawable.start()

    Glide.with(this)
        .load(imageUrl)
        .placeholder(circularProgressDrawable)
        .into(this)
}