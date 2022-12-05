package com.edy.oompaloompas.presentation.search

import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.presentation.home.OompaLoompaState

data class SearchScreenUiState(
    val isLoading: Boolean = true,
    val isFirstSearch: Boolean = true,
    val isLoadingPagination: Boolean = false,
    val oompaLoompas: WrappedStateList<OompaLoompaState> = WrappedStateList(list = emptyList()),
    val professions: WrappedStateList<String> = WrappedStateList(list = emptyList()),
    val genders: WrappedStateList<String> = WrappedStateList(list = emptyList()),
    val errorOompaLoompas: ErrorEntity? = null,
    val errorFilters: ErrorEntity? = null,
)


data class SearchComponentsUiState(
    val firstName: String? = null,
    val gender: String? = null,
    val profession: String? = null,
    val isExpanderProfession: Boolean = false
)