package com.ekades.movieapps.features.mainmovie.model

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("_postman_id")
    val postmanId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("schema")
    val schema: String,
    @SerializedName("_exporter_id")
    val exporterId: String
)