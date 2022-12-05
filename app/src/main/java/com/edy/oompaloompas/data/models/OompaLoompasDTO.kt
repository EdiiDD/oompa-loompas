package com.edy.oompaloompas.data.models

import com.squareup.moshi.Json

data class OompaLoompasDTO(
    val current: Int,
    val total: Int,
    val results: List<OompaLoompaDTO>,
)

data class OompaLoompaDTO(
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val favorite: FavoriteOompaLoompaDTO,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val id: Int
)


data class FavoriteOompaLoompaDTO(
    val color: String,
    val food: String,
    @Json(name = "random_string") val randomString: String,
    val song: String,
)

