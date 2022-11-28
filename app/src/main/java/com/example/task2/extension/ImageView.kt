package com.example.task2.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.task2.R


fun ImageView.loadCirclePicture(
    image: String,
    @DrawableRes placeholder: Int,
    @DrawableRes error: Int
) {
    Glide.with(context)
        .load(image)
        .circleCrop()
        .placeholder(placeholder)
        .error(error)
        .into(this)
}