package com.ekades.poststest.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photo(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable