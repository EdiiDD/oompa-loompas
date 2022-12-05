package com.edy.oompaloompas.presentation.favorites

import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.presentation.home.OompaLoompaState

data class FavoritesScreenUiState(
    val isLoading: Boolean = true,
    val isLoadingPagination: Boolean = false,
    val favoritesOompaLoompas: WrappedStateList<OompaLoompaState> = WrappedStateList(list = emptyList()),
    val error: ErrorEntity? = null,
)