package com.ekades.poststest.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var userName: String? = null,
    var userCompanyName: String? = null
) : Parcelable