package com.ekades.movieapps.features.moviedetail.state

import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieDetailResponse

sealed class MovieDetailVS {
    class ShowMovieDetail(val movieDetail: MovieDetailResponse) : MovieDetailVS()
    class Error(val message: String?) : MovieDetailVS()
    class ShowLoader(val showLoader: Boolean) : MovieDetailVS()
}