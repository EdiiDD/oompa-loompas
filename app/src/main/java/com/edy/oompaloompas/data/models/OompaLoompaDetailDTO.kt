package com.edy.oompaloompas.data.models

import com.squareup.moshi.Json

data class
OompaLoompaDetailDTO(
    @Json(name = "last_name") val lastName: String,
    val description: String,
    val image: String,
    val profession: String,
    val quota: String,
    val height: Int,
    @Json(name = "first_name") val firstName: String,
    val country: String,
    val age: Int,
    val favorite: FavoriteOompaLoompaDTO,
    val gender: String,
    val email: String,
)