package com.adammcneilly.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.api.load

class ImageLoader(private val imageView: ImageView) {
    fun load(imageUrl: String, placeholderDrawable: Drawable? = null) {
        imageView.load(imageUrl) {
            placeholder(placeholderDrawable)
        }
    }
}
