package com.edy.oompaloompas.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "oompa_loompas")
data class OompaLoompaLocalDTO(
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "favorite_color") val favoriteColor: String,
    @ColumnInfo(name = "favorite_food") val favoriteFood: String,
    @ColumnInfo(name = "favorite_random_string") val favoriteRandomString: String,
    @ColumnInfo(name = "favorite_song") val favoriteSong: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "profession") val profession: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
)