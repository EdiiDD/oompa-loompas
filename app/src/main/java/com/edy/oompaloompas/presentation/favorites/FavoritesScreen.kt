package com.edy.oompaloompas.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.edy.oompaloompas.R
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.presentation.detailoompaloompa.ProfessionOompaLoompa
import com.edy.oompaloompas.presentation.home.OompaLoompaState
import com.edy.oompaloompas.presentation.navigation.Destinations
import com.edy.oompaloompas.presentation.ui.base.BaseErrorScreen
import com.edy.oompaloompas.presentation.ui.base.BaseLoadingScreen
import com.edy.oompaloompas.presentation.ui.components.row.ItemOompaLoompa


@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    padding: PaddingValues,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    FavoritesOompaLoompasWrapperScreen(modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
        uiState = uiState,
        onClickSeeMoreOompaLoompas = { navController.navigate(Destinations.Home.route) },
        onClickOompaLoompa = {navController.navigate(Destinations.DetailOompaLoompa.createRoute(it))},
        onClickUnfavorite = { viewModel.updateUnfavorite(it) },
        onPagination = {  }
    )
}

@Composable
fun FavoritesOompaLoompasWrapperScreen(
    uiState: FavoritesScreenUiState,
    onClickSeeMoreOompaLoompas: () -> Unit,
    onClickOompaLoompa: (Int) -> Unit,
    onClickUnfavorite: (Int) -> Unit,
    onPagination: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    println("State: $uiState")
    Column(modifier = modifier.fillMaxWidth()) {

        if (uiState.isLoading) {
            BaseLoadingScreen()
        }

        uiState.error?.let { error ->
            BaseErrorScreen(error = error)
        }

        if (!uiState.isLoading && uiState.error == null) {
            FavoritesOompaLoompasSuccessScreen(
                oompaLoompas = uiState.favoritesOompaLoompas,
                favoritesOompaLoompas = uiState.favoritesOompaLoompas,
                isLoadingPagination = uiState.isLoadingPagination,
                onClickSeeMoreOompaLoompas = { onClickSeeMoreOompaLoompas() },
                onClickOompaLoompa = {
                    onClickOompaLoompa(it)
                },
                onClickUnfavorite = { onClickUnfavorite(it) },
                onPagination = { onPagination(it) }
            )
        }
    }

}

@Composable
fun FavoritesOompaLoompasSuccessScreen(
    oompaLoompas: WrappedStateList<OompaLoompaState>,
    favoritesOompaLoompas: WrappedStateList<OompaLoompaState>,
    isLoadingPagination: Boolean,
    onClickSeeMoreOompaLoompas: () -> Unit,
    onClickOompaLoompa: (Int) -> Unit,
    onClickUnfavorite: (Int) -> Unit,
    onPagination: (Int) -> Unit,
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.inversePrimary)
        .padding(horizontal = 12.dp, vertical = 8.dp)) {

        if (favoritesOompaLoompas.list.isNotEmpty()) {
            FavoritesOompaLoompas(
                favoritesOompaLoompas = oompaLoompas,
                isLoadingPagination = isLoadingPagination,
                onClickOompaLoompa = { onClickOompaLoompa(it) },
                onClickUnfavorite = { onClickUnfavorite(it) },
                onPagination = { onPagination(it) })
        } else {

            Text(
                text = stringResource(R.string.favorites_oompa_loompas),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge)

            Text(
                text = stringResource(R.string.title_empty_favorites_oompa_loompas),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge)

            Text(
                text = stringResource(R.string.message_empty_favorites_oompa_loompas),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium)

            Button(
                onClick = { onClickSeeMoreOompaLoompas() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.dp)
                    .padding(top = 12.dp),

                ) {
                Text(
                    text = stringResource(R.string.see_oompa_loompas),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun FavoritesOompaLoompas(
    favoritesOompaLoompas: WrappedStateList<OompaLoompaState>,
    isLoadingPagination: Boolean,
    onClickOompaLoompa: (Int) -> Unit,
    onClickUnfavorite: (Int) -> Unit,
    onPagination: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val oompaLoompasState = rememberLazyGridState()

    Column(modifier = modifier) {
        Text(text = stringResource(R.string.favorites_oompa_loompas),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge)

        Box {
            LazyVerticalGrid(columns = GridCells.Fixed(1),
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                state = oompaLoompasState,
                content = {
                    items(favoritesOompaLoompas.list.size) { index ->
                        onPagination(index)
                        println("Last visible: $index")
                        with(favoritesOompaLoompas.list[index]) {
                            ItemOompaLoompa(
                                oompaLoompa = this,
                                onClickFavorite = {},
                                onClickUnfavorite = { onClickUnfavorite(this.id) },
                                modifier = Modifier.clickable { onClickOompaLoompa(this.id) }
                            )
                        }
                    }
                })

            if (isLoadingPagination) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(95.dp)
                    .padding(24.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FavoritesOompaLoompasWrapperScreenPreview() {
    FavoritesOompaLoompasWrapperScreen(
        modifier = Modifier,
        uiState = FavoritesScreenUiState(
            isLoading = false,
            isLoadingPagination = false,
            favoritesOompaLoompas = WrappedStateList(list = MutableList(20) {
                OompaLoompaState(firstName = "Pepe",
                    lastName = "Pepote",
                    profession = ProfessionOompaLoompa.Developer,
                    image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
                    isFavorite = true,
                    id = 1)
            })
        ),
        onClickSeeMoreOompaLoompas = {},
        onClickOompaLoompa = {},
        onClickUnfavorite = {},
        onPagination = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyFavoritesOompaLoompasWrapperScreenPreview() {
    FavoritesOompaLoompasWrapperScreen(
        modifier = Modifier,
        uiState = FavoritesScreenUiState(
            isLoading = false,
            isLoadingPagination = false,
            favoritesOompaLoompas = WrappedStateList(emptyList())
        ),
        onClickSeeMoreOompaLoompas = {},
        onClickOompaLoompa = {},
        onClickUnfavorite = {},
        onPagination = {}
    )
}
