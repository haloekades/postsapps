package com.ekades.movieapps.networksV2.movie.domain.interactor

import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieListResponse
import com.ekades.movieapps.networksV2.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenreInteractor(
    private val movieRepository: MovieRepository
) : Interactor<GetMoviesByGenreInteractor.Params, Flow<MovieListResponse>> {

    override fun execute(params: GetMoviesByGenreInteractor.Params): Flow<MovieListResponse> {
        return movieRepository.getMoviesByGenre(
            page = params.page,
            genreId = params.genreId
        )
    }

    data class Params(
        val page: Int,
        val genreId: Int
    )
}