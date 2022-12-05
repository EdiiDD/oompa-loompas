package com.edy.oompaloompas.presentation.detailoompaloompa

import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.domain.models.OompaLoompaDetail

data class DetailOompaLoompaScreenUiState(
    val isLoading: Boolean = true,
    val oompaLoompa: OompaLoompaDetailState = OompaLoompaDetailState(),
    val error: ErrorEntity? = null,
)

data class OompaLoompaDetailState(
    val lastName: String,
    val description: String,
    val image: String,
    val profession: ProfessionOompaLoompa,
    val quota: String,
    val height: Int,
    val firstName: String,
    val country: String,
    val age: Int,
    val favoriteColor: String,
    val favoriteFood: String,
    val favoriteRandomString: String,
    val favoriteSong: String,
    val gender: GenderOompaLoompa,
    val email: String,
) {
    constructor() : this(
        lastName = "",
        description = "",
        image = "",
        profession = ProfessionOompaLoompa.Developer,
        quota = "",
        height = 0,
        firstName = "",
        country = "",
        age = 0,
        favoriteColor = "",
        favoriteFood = "",
        favoriteRandomString = "",
        favoriteSong = "",
        gender = GenderOompaLoompa.Female,
        email = ""
    )
}

fun OompaLoompaDetail.toOompaLoompaDetailState() = OompaLoompaDetailState(
    lastName = lastName,
    description = description,
    image = image,
    profession = profession.toProfessionOompaLoompa(),
    quota = quota,
    height = height,
    firstName = firstName,
    country = country,
    age = age,
    favoriteColor = favoriteColor,
    favoriteFood = favoriteFood,
    favoriteRandomString = favoriteRandomString,
    favoriteSong = favoriteSong,
    gender = gender.toGenderOompaLoompa(),
    email = email
)


sealed class GenderOompaLoompa {
    object Female : GenderOompaLoompa()
    object Male : GenderOompaLoompa()
}

sealed class ProfessionOompaLoompa {
    object Developer : ProfessionOompaLoompa()
    object Metalworker : ProfessionOompaLoompa()
    object Gemcutter : ProfessionOompaLoompa()
    object Medic : ProfessionOompaLoompa()
    object Brewer : ProfessionOompaLoompa()
}

fun String.toGenderOompaLoompa() = when (this) {
    "F" -> GenderOompaLoompa.Female
    "M" -> GenderOompaLoompa.Male
    else -> GenderOompaLoompa.Female
}

fun String.toProfessionOompaLoompa() = when (this) {
    "Developer" -> ProfessionOompaLoompa.Developer
    "Metalworker" -> ProfessionOompaLoompa.Metalworker
    "Gemcutter" -> ProfessionOompaLoompa.Gemcutter
    "Medic" -> ProfessionOompaLoompa.Medic
    "Brewer" -> ProfessionOompaLoompa.Brewer
    else -> ProfessionOompaLoompa.Developer
}