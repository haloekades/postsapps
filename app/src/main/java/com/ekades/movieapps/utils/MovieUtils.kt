package com.ekades.movieapps.utils

import com.ekades.movieapps.features.mainmovie.model.MovieDB
import com.ekades.movieapps.lib.application.ApplicationProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import java.lang.Exception
import java.util.*

object MovieUtils {

    private val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(Date::class.java, DateTypeAdapter()).create()

    val movieDB: MovieDB?
        get() = getMovieDBFromJson()

    private fun getMovieDBFromJson(): MovieDB? {
        return try {
            val movieDBJson: String =
                ApplicationProvider.context.assets.open("movie_postman_collection.json")
                    .bufferedReader()
                    .use { it.readText() }
            val movieDB = gson.fromJson(movieDBJson, MovieDB::class.java)
            movieDB
        } catch (e: Exception) {
            null
        }
    }
}