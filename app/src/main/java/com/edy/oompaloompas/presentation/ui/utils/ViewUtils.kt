package com.edy.oompaloompas.presentation.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt


fun String.toColor(): Color {
    return try {
        val colorInt = toColorInt()
        Color(colorInt)
    } catch (e: IllegalArgumentException) {
        Color.Transparent
    }
}