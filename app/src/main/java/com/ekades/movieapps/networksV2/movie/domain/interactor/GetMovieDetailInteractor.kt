package com.ekades.movieapps.networksV2.movie.domain.interactor

import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieDetailResponse
import com.ekades.movieapps.networksV2.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetailInteractor(
    private val movieRepository: MovieRepository
) : Interactor<GetMovieDetailInteractor.Params, Flow<MovieDetailResponse>> {

    override fun execute(params: GetMovieDetailInteractor.Params): Flow<MovieDetailResponse> {
        return movieRepository.getMoviesDetail(
            movieId = params.movieId
        )
    }

    data class Params(
        val movieId: Int
    )
}