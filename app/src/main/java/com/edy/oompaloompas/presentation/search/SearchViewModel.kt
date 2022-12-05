package com.edy.oompaloompas.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.domain.interactors.GetAllGendersUseCase
import com.edy.oompaloompas.domain.interactors.GetAllProfessionsUseCase
import com.edy.oompaloompas.domain.interactors.GetFilterOompaLoompasUseCase
import com.edy.oompaloompas.domain.interactors.Input
import com.edy.oompaloompas.presentation.home.HomeScreenUiState
import com.edy.oompaloompas.presentation.home.toOompaLoompaState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllProfessionOompaLoompa: GetAllProfessionsUseCase,
    private val getAllGendersUseCase: GetAllGendersUseCase,
    private val getFilterOompaLoompasUseCase: GetFilterOompaLoompasUseCase,
) : ViewModel() {

    // Get all professiones and all genders from local data base
    private val professions = getAllProfessionOompaLoompa.execute(Unit)
    private val genders = getAllGendersUseCase.execute(Unit)

    // UiState for exposed state
    private val mutableUiState = MutableStateFlow(SearchScreenUiState())
    val uiState: StateFlow<SearchScreenUiState> = mutableUiState.asStateFlow()

    // UiState for exposed state
    private val mutableUiComponentState = MutableStateFlow(SearchComponentsUiState())
    val uiComponentsState: StateFlow<SearchComponentsUiState> = mutableUiComponentState.asStateFlow()


    init {

        // Combine getAllProfessionOompaLoompa and getAllGendersUseCase
        combine(
            professions,
            genders
        ) { professions, genders ->

            var allProfessions = emptyList<String>()
            var allGenders = emptyList<String>()

            var errorFilters: ErrorEntity? = null

            // Success data from getOompaLoompasUseCase
            if (professions is Resource.Success) {
                allProfessions = professions.data
            }

            // Success data from getFavoritesOompaLoompasUseCase
            if (genders is Resource.Success) {
                allGenders = genders.data
            }

            // Error from getOompaLoompasUseCase or getFavoritesOompaLoompasUseCase
            if (professions is Resource.Error && genders is Resource.Error) {
                errorFilters = ErrorEntity.LocalDataBaseError.MissingLocalStorageError
            }

            mutableUiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    professions = WrappedStateList(allProfessions),
                    genders = WrappedStateList(allGenders),
                    errorFilters = errorFilters
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = HomeScreenUiState()
        ).launchIn(viewModelScope)
    }

    // Udpate first name filter and filter
    fun updateFirstName(firstName: String) {
        mutableUiComponentState.update { currentState ->
            currentState.copy(
                firstName = firstName
            )
        }
        getOompaLoompasWithFilter()
    }

    // Update gender name filter and filter
    fun updateGenders(gender: String) {
        mutableUiComponentState.update { currentState ->
            currentState.copy(
                gender = gender
            )
        }
        getOompaLoompasWithFilter()
    }

    // Update profession name filter and filter
    fun updateProfessions(profession: String) {
        mutableUiComponentState.update { currentState ->
            currentState.copy(
                profession = profession,
                isExpanderProfession = false
            )
        }
        getOompaLoompasWithFilter()
    }

    //Upate isExpanderProfession for show or not in the screen
    fun updateExpandProfession(boolean: Boolean) {
        mutableUiComponentState.update { currentState ->
            currentState.copy(
                isExpanderProfession = !currentState.isExpanderProfession
            )
        }
    }

    // Execute use case with filters
    private fun getOompaLoompasWithFilter() {
        getFilterOompaLoompasUseCase
            .execute(
                Input(
                    firstName = mutableUiComponentState.value.firstName,
                    gender = mutableUiComponentState.value.gender,
                    profession = mutableUiComponentState.value.profession,
                ))
            .onStart { }
            .onEach { result ->
                println(result)
                when (result) {
                    is Resource.Error ->
                        mutableUiState.update { currentState ->
                            currentState.copy(
                                errorOompaLoompas = result.error
                            )
                        }

                    is Resource.Success ->
                        mutableUiState.update { currentState ->
                            currentState.copy(
                                oompaLoompas = WrappedStateList(result.data.results.map { it.toOompaLoompaState() })
                            )
                        }
                }
            }
            .onCompletion { }
            .launchIn(viewModelScope)
    }

}