package com.ekades.poststest.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
) : Parcelable