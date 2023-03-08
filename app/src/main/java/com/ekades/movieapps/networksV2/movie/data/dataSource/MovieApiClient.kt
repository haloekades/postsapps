package com.ekades.movieapps.networksV2.movie.data.dataSource

import com.ekades.movieapps.lib.core.networkV2.BaseApiClient
import com.ekades.movieapps.networksV2.movie.data.dataSource.interfaces.IMovieApiClient

object MovieApiClient : BaseApiClient<IMovieApiClient>(IMovieApiClient::class.java)