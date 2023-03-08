package com.ekades.movieapps.networksV2.movie.data.dataSource

import com.ekades.movieapps.features.genre.model.GenreModel
import com.ekades.movieapps.lib.core.networkV2.BaseApiClient.Companion.BASE_URL_MOVIE
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieDetailResponse
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDataStore {

    fun getAllGenres(): Flow<List<GenreModel>> = flow {
        MovieApiClient.getApiClient(BASE_URL_MOVIE).getAllGenres().genres.apply {
            emit(this)
        }
    }

    fun getMoviesByGenre(page: Int, genreId: Int): Flow<MovieListResponse> = flow {
        MovieApiClient.getApiClient(BASE_URL_MOVIE).getMoviesByGenre(
            page = page,
            genreId = genreId
        ).apply {
            emit(this)
        }
    }

    fun getMoviesDetail(movieId: Int): Flow<MovieDetailResponse> = flow {
        MovieApiClient.getApiClient(BASE_URL_MOVIE).getMovieDetail(
            movieId = movieId
        ).apply {
            emit(this)
        }
    }
}