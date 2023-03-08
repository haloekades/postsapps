package com.ekades.movieapps.networksV2.movie.data.dataSource.interfaces

import com.ekades.movieapps.networksV2.movie.data.dataSource.response.GenresResponse
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieDetailResponse
import com.ekades.movieapps.networksV2.movie.data.dataSource.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieApiClient {

    @GET("genre/movie/list")
    suspend fun getAllGenres(
        @Query("api_key") apiKey: String = API_KEY
    ): GenresResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailResponse


    companion object {
        private const val API_KEY = "be8b6c8aa9a5f4e240bb6093f9849051"
    }
}