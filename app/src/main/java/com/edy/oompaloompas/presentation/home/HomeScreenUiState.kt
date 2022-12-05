package com.edy.oompaloompas.presentation.home

import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.domain.models.OompaLoompa
import com.edy.oompaloompas.presentation.detailoompaloompa.ProfessionOompaLoompa
import com.edy.oompaloompas.presentation.detailoompaloompa.toProfessionOompaLoompa

data class HomeScreenUiState(
    val isLoading: Boolean = true,
    val isLoadingPagination: Boolean = false,
    val oompaLoompas: WrappedStateList<OompaLoompaState> = WrappedStateList(list = emptyList()),
    val favoritesOompaLoompas: WrappedStateList<OompaLoompaState> = WrappedStateList(list = emptyList()),
    val errorOompaLoompas: ErrorEntity? = null,
    val errorFavoritesOompaLoompas: ErrorEntity? = null,
    val isSuccessPostfavorite: Boolean? = null,
    val errorPostfavorite: ErrorEntity? = null,
)

data class OompaLoompaState(
    val firstName: String,
    val lastName: String,
    val profession: ProfessionOompaLoompa,
    val image: String,
    var isFavorite: Boolean,
    val id: Int,
)

fun OompaLoompa.toOompaLoompaState() = OompaLoompaState(
    firstName,
    lastName,
    profession.toProfessionOompaLoompa(),
    image,
    isFavorite,
    id
)

