package com.edy.oompaloompas.data.mappers


import com.edy.oompaloompas.data.models.OompaLoompaDetailDTO
import com.edy.oompaloompas.data.models.OompaLoompaLocalDTO
import com.edy.oompaloompas.domain.models.OompaLoompa
import com.edy.oompaloompas.domain.models.OompaLoompaDetail

/*
Mapper all data from the data layer to domain
The data can come from local data store or remote data store
*/

fun OompaLoompaLocalDTO.toOompaLoompa() = OompaLoompa(
    firstName = firstName,
    lastName = lastName,
    favoriteColor = favoriteColor,
    favoriteFood = favoriteFood,
    favoriteRandomString = favoriteRandomString,
    favoriteSong = favoriteSong,
    gender = gender,
    image = image,
    profession = profession,
    email = email,
    age = age,
    country = country,
    height = height,
    isFavorite = isFavorite,
    id = id
)

fun OompaLoompaDetailDTO.toOompaLoompaDetail() = OompaLoompaDetail(
    lastName = lastName,
    description = description,
    image = image,
    profession = profession,
    quota = quota,
    height = height,
    firstName = firstName,
    country = country,
    age = age,
    favoriteColor = favorite.color,
    favoriteFood = favorite.food,
    favoriteRandomString = favorite.randomString,
    favoriteSong = favorite.song,
    gender = gender,
    email = email
)