package com.edy.oompaloompas.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.edy.oompaloompas.R
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.presentation.home.OompaLoompas
import com.edy.oompaloompas.presentation.navigation.Destinations
import com.edy.oompaloompas.presentation.ui.base.BaseErrorScreen
import com.edy.oompaloompas.presentation.ui.base.BaseLoadingScreen

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    padding: PaddingValues,
    navController: NavHostController,
) {

    val uiState by viewModel.uiState.collectAsState()
    val uiComponentState by viewModel.uiComponentsState.collectAsState()

    SearchlWrapperScreen(
        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
        uiState = uiState,
        uiComponentState = uiComponentState,
        onValueChangeFirstName = { viewModel.updateFirstName(it) },
        onClickGender = { viewModel.updateGenders(it) },
        onClickProfession = { viewModel.updateProfessions(it) },
        onClickExpandProfession = { viewModel.updateExpandProfession(it) },
        onClickItem = { navController.navigate(Destinations.DetailOompaLoompa.createRoute(it)) }
    )
}

@Composable
fun SearchlWrapperScreen(
    uiState: SearchScreenUiState,
    uiComponentState: SearchComponentsUiState,
    onValueChangeFirstName: (String) -> Unit,
    onClickGender: (String) -> Unit,
    onClickProfession: (String) -> Unit,
    onClickExpandProfession: (Boolean) -> Unit,
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {

        println("UiState: $uiState")

        if (uiState.isLoading) {
            BaseLoadingScreen()
        }

        uiState.errorFilters?.let {
            BaseErrorScreen(error = it)
        }

        if (!uiState.isLoading && uiState.errorFilters == null && uiState.errorOompaLoompas == null) {
            SearchSuccessScreen(
                oompaLoompa = uiState,
                uiComponentState = uiComponentState,
                onValueChangeFirstName = { onValueChangeFirstName(it) },
                onClickGender = { onClickGender(it) },
                onClickProfession = { onClickProfession(it) },
                onClickExpandProfession = { onClickExpandProfession(it) },
                onClickItem = { onClickItem(it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchSuccessScreen(
    oompaLoompa: SearchScreenUiState,
    uiComponentState: SearchComponentsUiState,
    onValueChangeFirstName: (String) -> Unit,
    onClickGender: (String) -> Unit,
    onClickProfession: (String) -> Unit,
    onClickExpandProfession: (Boolean) -> Unit,
    onClickItem: (Int) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(horizontal = 12.dp, vertical = 8.dp)) {
        TextField(
            value = uiComponentState.firstName ?: "",
            onValueChange = { onValueChangeFirstName(it) },
            label = {
                Text(
                    text = stringResource(R.string.first_name),
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (oompaLoompa.genders.list.isNotEmpty()) {
            Text(
                text = stringResource(R.string.genders),
                modifier = Modifier.padding(top=4.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                oompaLoompa.genders.list.forEach { gender ->

                    Text(
                        text = gender,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Checkbox(
                        checked = uiComponentState.gender == gender,
                        onCheckedChange = { onClickGender(gender) },
                    )
                }
            }

        }

        if (oompaLoompa.professions.list.isNotEmpty()) {

            Text(
                text = pluralStringResource(R.plurals.profession_title, 1),
                modifier = Modifier.padding(top=4.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = uiComponentState.profession ?: stringResource(R.string.all),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onClickExpandProfession(uiComponentState.isExpanderProfession) },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            Box(modifier = Modifier.fillMaxWidth()) {

                DropdownMenu(
                    expanded = uiComponentState.isExpanderProfession,
                    onDismissRequest = { onClickExpandProfession(uiComponentState.isExpanderProfession) }
                ) {
                    oompaLoompa.professions.list.forEach { profession ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = profession,
                                    modifier = Modifier,
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            onClick = { onClickProfession(profession) }
                        )
                    }

                }

            }
        }

        OompaLoompas(
            oompaLoompas = oompaLoompa.oompaLoompas,
            isLoadingPagination = oompaLoompa.isLoadingPagination,
            onClickOompaLoompa = { onClickItem(it) },
            onClickFavorite = { },
            onClickUnfavorite = { },
            onPagination = { },
            modifier = Modifier.padding(top=4.dp)
        )
    }
}


@Preview
@Composable
fun SearchSuccessScreenPreview() {
    SearchSuccessScreen(
        SearchScreenUiState(
            isLoading = false,
            isFirstSearch = true,
            isLoadingPagination = false,
            oompaLoompas = WrappedStateList(emptyList()),
            professions = WrappedStateList(listOf("Developer", "Metalworker", "Gemcutter", "Medic", "Brewer")),
            genders = WrappedStateList(listOf("F", "M")),
            errorOompaLoompas = null,
            errorFilters = null
        ),
        SearchComponentsUiState(
            firstName = "Paco",
            gender = "F",
            profession = "Developer",
            isExpanderProfession = true
        ),
        onValueChangeFirstName = {},
        onClickGender = {},
        onClickProfession = {},
        onClickExpandProfession = {},
        onClickItem = {}
    )
}