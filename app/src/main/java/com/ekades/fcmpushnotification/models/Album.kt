package com.ekades.fcmpushnotification.models

class Album(
    val userId: Int,
    val id: Int,
    val title: String,
    var photos: ArrayList<Photo>? = null
)