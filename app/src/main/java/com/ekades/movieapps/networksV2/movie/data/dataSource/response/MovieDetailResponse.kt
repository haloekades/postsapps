package com.ekades.movieapps.networksV2.movie.data.dataSource.response

import com.ekades.movieapps.features.genre.model.GenreModel

data class MovieDetailResponse(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val budget: Long,
    val genres: List<GenreModel>,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)