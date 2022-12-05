package com.edy.oompaloompas.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edy.oompaloompas.R

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val destination: Destinations,
)

@Composable
fun BottomAppBarOompaLoompas(
    navController: NavController,
) {

    //Create items for bottom bar.
    val tabItems = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.favorites),
            icon = Icons.Filled.Favorite,
            destination = Destinations.Favorites
        ),
        BottomNavigationItem(
            title = stringResource(R.string.home),
            icon = Icons.Filled.Home,
            destination = Destinations.Home
        ),
        BottomNavigationItem(
            title = stringResource(R.string.search),
            icon = Icons.Filled.Search,
            destination = Destinations.Search
        ),
        BottomNavigationItem(
            title = stringResource(R.string.settings),
            icon = Icons.Filled.Settings,
            destination = Destinations.Settings
        )
    )

    val currentRoute = getCurrentRoute(navController)

    if (tabItems.map { it.destination.route }.contains(currentRoute)) {
        BottomAppBarItems(
            tabItems = tabItems,
            currentRoute = currentRoute,
            navController
        )
    }
}

@Composable
fun BottomAppBarItems(
    tabItems: List<BottomNavigationItem>,
    currentRoute: String?,
    navController: NavController
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,

        ) {
        tabItems.forEach { tabItem ->
            NavigationBarItem(
                selected = currentRoute == tabItem.destination.route,
                onClick = {
                    when (tabItem.destination) {
                        Destinations.Favorites -> navController.navigate(Destinations.Favorites.route)
                        Destinations.Home -> navController.navigate(Destinations.Home.route)
                        Destinations.Settings -> navController.navigate(Destinations.Settings.route)
                        Destinations.Search -> navController.navigate(Destinations.Search.route)
                        else -> navController.navigate(Destinations.Home.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = tabItem.icon,
                        contentDescription = tabItem.title
                    )
                },
                label = {
                    Text(text = tabItem.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.surface,
                    unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                )
            )
        }
    }
}

@Composable
private fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route

}

@Preview(showBackground = true)
@Composable
fun BottomAppBarOompaLoompasPreview() {
    BottomAppBarItems(
        tabItems = listOf(
            BottomNavigationItem(
                title = stringResource(R.string.favorites),
                icon = Icons.Filled.Favorite,
                destination = Destinations.Favorites
            ),
            BottomNavigationItem(
                title = stringResource(R.string.home),
                icon = Icons.Filled.Home,
                destination = Destinations.Home
            ),
            BottomNavigationItem(
                title = stringResource(R.string.search),
                icon = Icons.Filled.Search,
                destination = Destinations.Search
            ),
            BottomNavigationItem(
                title = stringResource(R.string.settings),
                icon = Icons.Filled.Settings,
                destination = Destinations.Settings
            )
        ),
        currentRoute = Destinations.Home.route,
        navController = rememberNavController()
    )
}