package com.ekades.movieapps.networksV2.movie.domain.repository

import com.ekades.movieapps.features.genre.model.GenreModel
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieDetailResponse
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieListResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllGenres(): Flow<List<GenreModel>>

    fun getMoviesByGenre(page: Int, genreId: Int): Flow<MovieListResponse>

    fun getMoviesDetail(movieId: Int): Flow<MovieDetailResponse>

}