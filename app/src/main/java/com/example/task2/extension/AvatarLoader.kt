package com.example.task2.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.task2.R


 fun ImageView.loadPicture(image: String) {
    Glide.with(this.context)
        .load(image)
        .circleCrop()
        .placeholder(R.drawable.ic_user_avatar)
        .error(R.drawable.ic_user_avatar)
        .into(this)
}