package com.ekades.movieapps.features.genre.state

import com.ekades.movieapps.features.genre.model.GenreModel

sealed class GenreVS {
    class ShowGenres(val genres: List<GenreModel>) : GenreVS()
    class Error(val message: String?) : GenreVS()
    class ShowLoader(val showLoader: Boolean) : GenreVS()
}