package com.ekades.movieapps.networksV2.movie.data.dataSource.response

import com.ekades.movieapps.features.movielist.model.MovieModel

data class MovieListResponse(
    val page: Int,
    val results: List<MovieModel>
)