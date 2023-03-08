package com.ekades.movieapps.features.movielist.state

import com.ekades.movieapps.features.movielist.model.MovieModel

sealed class MovieListVS {
    class ShowMovies(val movies: List<MovieModel>) : MovieListVS()
    class Error(val message: String?) : MovieListVS()
    class ShowLoader(val showLoader: Boolean) : MovieListVS()
}