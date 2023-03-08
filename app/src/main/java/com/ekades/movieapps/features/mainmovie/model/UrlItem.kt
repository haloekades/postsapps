package com.ekades.movieapps.features.mainmovie.model

data class UrlItem(
    val raw: String,
    val protocol: String,
    val host: ArrayList<String>,
    val path: ArrayList<String>,
    val query: ArrayList<Query>
)