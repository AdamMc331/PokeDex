package com.adammcneilly.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader(private val imageView: ImageView) {
    fun load(imageUrl: String, placeholderDrawable: Drawable? = null) {
        Glide.with(imageView)
            .load(imageUrl)
            .placeholder(placeholderDrawable)
            .into(imageView)
    }
}