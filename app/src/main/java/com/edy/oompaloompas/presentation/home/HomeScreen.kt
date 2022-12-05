package com.edy.oompaloompas.presentation.home

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
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import com.edy.oompaloompas.presentation.navigation.Destinations
import com.edy.oompaloompas.presentation.ui.base.BaseLoadingScreen
import com.edy.oompaloompas.presentation.ui.components.row.ItemFavoriteOompaLoompa
import com.edy.oompaloompas.presentation.ui.components.row.ItemOompaLoompa

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    padding: PaddingValues,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    OompaLoompasWrapperScreen(
        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
        uiState = uiState,
        onClickOompaLoompa = { navController.navigate(Destinations.DetailOompaLoompa.createRoute(it)) },
        onClickFavorite = { viewModel.updateFavorite(it) },
        onClickUnfavorite = { viewModel.updateUnfavorite(it) },
        onPagination = { viewModel.updateLastItemVisible(it) }
    )
}

@Composable
fun OompaLoompasWrapperScreen(
    uiState: HomeScreenUiState,
    onClickOompaLoompa: (Int) -> Unit,
    onClickFavorite: (Int) -> Unit,
    onClickUnfavorite: (Int) -> Unit,
    onPagination: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {

        if (uiState.isLoading) {
            BaseLoadingScreen()
        }


        if (!uiState.isLoading) {
            OompaLoompasSuccessScreen(oompaLoompas = uiState.oompaLoompas,
                favoritesOompaLoompas = uiState.favoritesOompaLoompas,
                isLoadingPagination = uiState.isLoadingPagination,
                onClickOompaLoompa = {
                    onClickOompaLoompa(it)
                },
                onClickFavorite = { onClickFavorite(it) },
                onClickUnfavorite = { onClickUnfavorite(it) },
                onPagination = { onPagination(it) }
            )
        }
    }
}

@Composable
fun OompaLoompasSuccessScreen(
    oompaLoompas: WrappedStateList<OompaLoompaState>,
    favoritesOompaLoompas: WrappedStateList<OompaLoompaState>,
    isLoadingPagination: Boolean,
    onClickOompaLoompa: (Int) -> Unit,
    onClickFavorite: (Int) -> Unit,
    onClickUnfavorite: (Int) -> Unit,
    onPagination: (Int) -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.inversePrimary)
        .padding(horizontal = 12.dp, vertical = 8.dp)) {

        if (favoritesOompaLoompas.list.isNotEmpty()) {
            FavoritesOompaLoompas(
                favoritesOompaLoompas = favoritesOompaLoompas,
                onClickMore = {},
                onClickOompaLoompa = { onClickOompaLoompa(it) }
            )
        }

        OompaLoompas(
            oompaLoompas = oompaLoompas,
            isLoadingPagination = isLoadingPagination,
            onClickOompaLoompa = { onClickOompaLoompa(it) },
            onClickFavorite = { onClickFavorite(it) },
            onClickUnfavorite = { onClickUnfavorite(it) },
            onPagination = { onPagination(it) }
        )
    }
}

@Composable
fun FavoritesOompaLoompas(
    favoritesOompaLoompas: WrappedStateList<OompaLoompaState>,
    onClickMore: () -> Unit,
    onClickOompaLoompa: (Int) -> Unit,
) {
    Text(text = stringResource(R.string.favorites_oompa_loompas),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineLarge)

    LazyHorizontalGrid(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        rows = GridCells.Fixed(1),
        content = {
            items(
                favoritesOompaLoompas.list.size, key = { it }
            ) { index ->
                with(favoritesOompaLoompas.list[index]) {
                    ItemFavoriteOompaLoompa(
                        oompaLoompa = this,
                        modifier = Modifier.clickable { onClickOompaLoompa(this.id) }
                    )
                }
            }
        },
        modifier = Modifier.height(145.dp))
}

@Composable
fun OompaLoompas(
    oompaLoompas: WrappedStateList<OompaLoompaState>,
    isLoadingPagination: Boolean,
    onClickOompaLoompa: (Int) -> Unit,
    onClickFavorite: (Int) -> Unit,
    onClickUnfavorite: (Int) -> Unit,
    onPagination: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val oompaLoompasState = rememberLazyGridState()

    Column(modifier = modifier) {
        Text(text = stringResource(R.string.home_oompa_loompas),
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
                    items(oompaLoompas.list.size) { index ->
                        onPagination(index)
                        println("Last visible: $index")
                        with(oompaLoompas.list[index]) {
                            ItemOompaLoompa(
                                oompaLoompa = this,
                                onClickFavorite = { onClickFavorite(this.id) },
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
fun OompaLoompasWrapperScreenPreview() {
    OompaLoompasWrapperScreen(
        modifier = Modifier,
        uiState = HomeScreenUiState(isLoading = false, isLoadingPagination = false, oompaLoompas = WrappedStateList(list = MutableList(20) {
            OompaLoompaState(firstName = "Pepe",
                lastName = "Pepote",
                profession = ProfessionOompaLoompa.Medic,
                image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
                isFavorite = true,
                id = 1)
        }), favoritesOompaLoompas = WrappedStateList(list = MutableList(20) {
            OompaLoompaState(firstName = "Carlos",
                lastName = "Pepote",
                profession = ProfessionOompaLoompa.Medic,
                image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
                isFavorite = true,
                id = 1)
        })),
        onClickOompaLoompa = {},
        onClickFavorite = {},
        onClickUnfavorite = {},
        onPagination = {}
    )
}