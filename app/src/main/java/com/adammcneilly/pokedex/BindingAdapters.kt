package com.adammcneilly.pokedex

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

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