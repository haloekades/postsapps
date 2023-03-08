package com.ekades.movieapps.networksV2.movie.domain.interactor

import com.ekades.movieapps.features.genre.model.GenreModel
import com.ekades.movieapps.lib.core.networkV2.interactor.Interactor
import com.ekades.movieapps.networksV2.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetAllGenresInteractor(
    private val movieRepository: MovieRepository
) : Interactor<Interactor.None, Flow<List<GenreModel>>> {

    override fun execute(params: Interactor.None): Flow<List<GenreModel>> {
        return movieRepository.getAllGenres()
    }
}