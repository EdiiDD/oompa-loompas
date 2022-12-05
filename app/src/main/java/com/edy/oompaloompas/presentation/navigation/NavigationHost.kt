package com.edy.oompaloompas.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.edy.oompaloompas.presentation.detailoompaloompa.DetailOompaLoompaScreen
import com.edy.oompaloompas.presentation.favorites.FavoritesScreen
import com.edy.oompaloompas.presentation.home.HomeScreen
import com.edy.oompaloompas.presentation.navigation.Destinations.DetailOompaLoompa
import com.edy.oompaloompas.presentation.navigation.Destinations.Favorites
import com.edy.oompaloompas.presentation.navigation.Destinations.Home
import com.edy.oompaloompas.presentation.navigation.Destinations.Search
import com.edy.oompaloompas.presentation.navigation.Destinations.Settings
import com.edy.oompaloompas.presentation.search.SearchScreen
import com.edy.oompaloompas.presentation.settings.SettingsScreen

@Composable
fun OompaLoompasNavigation(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        composable(Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                padding = padding,
                navController = navController,
            )
        }

        composable(Favorites.route) {
            FavoritesScreen(
                viewModel = hiltViewModel(),
                padding = padding,
                navController = navController,
            )
        }

        composable(Search.route) {
            SearchScreen(
                viewModel = hiltViewModel(),
                padding = padding,
                navController = navController,
            )
        }

        composable(Settings.route) {
            SettingsScreen()
        }

        composable(
            route = DetailOompaLoompa.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            DetailOompaLoompaScreen(
                viewModel = hiltViewModel(),
                padding = padding,
                idOompaLoompa = it.arguments?.getInt("id")
            )
        }
    }
}