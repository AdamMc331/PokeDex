package com.adammcneilly.pokedex

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibilityCondition")
fun View.visibleIf(condition: Boolean?) {
    this.visibility = if (condition == true) View.VISIBLE else View.GONE
}