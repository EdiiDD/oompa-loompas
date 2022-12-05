package com.edy.oompaloompas.presentation.navigation

sealed class Destinations(
    val route: String,
) {
    object Home : Destinations("home")
    object Favorites : Destinations("favorites")
    object Settings : Destinations("settings")
    object Search : Destinations("search")
    object DetailOompaLoompa : Destinations("oompaLoompa/?id={id}"){
        fun createRoute(id: Int?) = "oompaLoompa/?id=$id"
    }
}