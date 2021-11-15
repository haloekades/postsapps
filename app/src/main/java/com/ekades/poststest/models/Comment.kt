package com.ekades.poststest.models

class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    var body: String
)