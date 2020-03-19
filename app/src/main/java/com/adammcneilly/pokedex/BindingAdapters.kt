package com.adammcneilly.pokedex

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.adammcneilly.imageloader.ImageLoader
import com.adammcneilly.pokedex.core.Type
import com.adammcneilly.pokedex.models.colorRes
import com.adammcneilly.pokedex.models.complimentaryColorRes

private const val PROGRESS_STROKE_WIDTH = 10F
private const val PROGRESS_CENTER_RADIUS = 30F
private const val PILL_RADIUS = 120F

@BindingAdapter("visibilityCondition")
fun View.visibleIf(condition: Boolean?) {
    this.visibility = if (condition == true) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) return

    val progressColor = ContextCompat.getColor(context, R.color.colorAccent)

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = PROGRESS_STROKE_WIDTH
    circularProgressDrawable.centerRadius = PROGRESS_CENTER_RADIUS
    circularProgressDrawable.setColorFilter(progressColor, PorterDuff.Mode.SRC_IN)
    circularProgressDrawable.start()

    ImageLoader(this).load(
        imageUrl = imageUrl,
        placeholderDrawable = circularProgressDrawable
    )
}

@SuppressLint("DefaultLocale")
@BindingAdapter("pokemonType")
fun TextView.bindPokemonType(type: Type?) {
    if (type == null) return

    this.text = type.name.capitalize()
    this.setTextColor(ContextCompat.getColor(context, type.complimentaryColorRes()))

    val shape = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = PILL_RADIUS
        setColor(ContextCompat.getColor(context, type.colorRes()))
        setStroke(1, ContextCompat.getColor(context, R.color.mds_white))
    }
    this.background = shape
}