package com.ekades.ruangmuslim.lib.core.ui.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekades.ruangmuslim.lib.core.ui.R

fun ImageView.loadImageUrlWithPlaceHolder(
    photoUrl: String,
    idPlaceHolder: Int = R.drawable.ic_basic_place_holder
) {
    Glide.with(context)
        .asBitmap()
        .load(photoUrl)
        .apply(
            RequestOptions()
                .placeholder(idPlaceHolder)
        )
        .into(this)
}
