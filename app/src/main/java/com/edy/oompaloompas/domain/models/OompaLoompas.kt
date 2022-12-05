package com.edy.oompaloompas.domain.models

data class OompaLoompas(
    val results: List<OompaLoompa>,
)

data class OompaLoompa(
    val firstName: String,
    val lastName: String,
    val favoriteColor: String,
    val favoriteFood: String,
    val favoriteRandomString: String,
    val favoriteSong: String,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val isFavorite: Boolean,
    val id: Int,
)