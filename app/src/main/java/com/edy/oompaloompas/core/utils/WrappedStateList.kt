package com.edy.oompaloompas.core.utils

import androidx.compose.runtime.Immutable

// Wrapper class for list ->  for uiState with jetpack compose
@Immutable
data class WrappedStateList<T>(
    val list: List<T> = listOf()
)
