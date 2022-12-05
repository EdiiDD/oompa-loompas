package com.edy.oompaloompas.data.mappers

import com.edy.oompaloompas.data.models.OompaLoompaDTO
import com.edy.oompaloompas.data.models.OompaLoompaLocalDTO


fun OompaLoompaDTO.toOompaLoompa() = OompaLoompaLocalDTO(
    firstName = firstName,
    lastName = lastName,
    favoriteColor = favorite.color,
    favoriteFood = favorite.food,
    favoriteRandomString = favorite.randomString,
    favoriteSong = favorite.song,
    gender = gender,
    image = image,
    profession = profession,
    email = email,
    age = age,
    country = country,
    height = height,
    isFavorite = false,
    id = id
)

