package com.ekades.movieapps.di

import com.ekades.movieapps.features.genre.GenreViewModel
import com.ekades.movieapps.features.moviedetail.MovieDetailViewModel
import com.ekades.movieapps.features.movielist.MovieListViewModel
import com.ekades.movieapps.networksV2.movie.data.dataSource.MovieDataStore
import com.ekades.movieapps.networksV2.movie.data.repository.MovieRepositoryImpl
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetAllGenresInteractor
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetMovieDetailInteractor
import com.ekades.movieapps.networksV2.movie.domain.interactor.GetMoviesByGenreInteractor
import com.ekades.movieapps.networksV2.movie.domain.repository.MovieRepository
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val postModule = module {

    // Start Movie //
    viewModel {
        GenreViewModel(get())
    }

    viewModel {
        MovieListViewModel(get())
    }

    viewModel {
        MovieDetailViewModel(get())
    }

    single {
        GetAllGenresInteractor(get())
    }

    single {
        GetMoviesByGenreInteractor(get())
    }

    single {
        GetMovieDetailInteractor(get())
    }

    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    single {
        MovieDataStore()
    }

    // End Movie
}

val modules = listOf(postModule)