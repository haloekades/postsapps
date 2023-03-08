package com.ekades.movieapps.networksV2.movie.data.repository

import com.ekades.movieapps.features.genre.model.GenreModel
import com.ekades.movieapps.networksV2.movie.data.dataSource.MovieDataStore
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieDetailResponse
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieListResponse
import com.ekades.movieapps.networksV2.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieDataStore: MovieDataStore
) : MovieRepository {

    override fun getAllGenres(): Flow<List<GenreModel>> = movieDataStore.getAllGenres()

    override fun getMoviesByGenre(page: Int, genreId: Int): Flow<MovieListResponse> =
        movieDataStore.getMoviesByGenre(page, genreId)

    override fun getMoviesDetail(movieId: Int): Flow<MovieDetailResponse> =
        movieDataStore.getMoviesDetail(movieId)
}
