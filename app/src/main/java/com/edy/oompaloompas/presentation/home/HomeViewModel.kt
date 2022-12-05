package com.edy.oompaloompas.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.domain.interactors.GetFavoritesOompaLoompasWithLimitUseCase
import com.edy.oompaloompas.domain.interactors.GetOompaLoompasUseCase
import com.edy.oompaloompas.domain.interactors.PostFavoriteUseCase
import com.edy.oompaloompas.domain.interactors.PostUnfavoriteUseCase
import com.edy.oompaloompas.domain.interactors.UpdateLoompasUseCase
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
class HomeViewModel @Inject constructor(
    private val getOompaLoompasUseCase: GetOompaLoompasUseCase,
    private val getFavoritesOompaLoompasUseCase: GetFavoritesOompaLoompasWithLimitUseCase,
    private val postFavoriteUseCase: PostFavoriteUseCase,
    private val postUnfavoriteUseCase: PostUnfavoriteUseCase,
    private val updateLoompasUseCase: UpdateLoompasUseCase,
) : ViewModel() {


    // UiState for exposed state
    private val mutableUiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = mutableUiState.asStateFlow()

    init {
        updateLastItemVisible(0)
        combineOompaLoompasAndFavorite()
    }

    fun combineOompaLoompasAndFavorite() {

        // Get Oompa Loompas and Favorites from Local Data Base
        val oompaLoompasUseCase = getOompaLoompasUseCase.execute(Unit)
        val favoritesOompaLoompasUseCase = getFavoritesOompaLoompasUseCase.execute(Unit)

        // Combine getOompaLoompasUseCase and getFavoritesOompaLoompasUseCase
        combine(oompaLoompasUseCase, favoritesOompaLoompasUseCase) { oompaLoompas, favoritesOompaLoompas ->

            var oompaLoompasStateList = emptyList<OompaLoompaState>()
            var favoritesOompaLoompasStateList = emptyList<OompaLoompaState>()

            var errorOompaLoompas: ErrorEntity? = null
            var errorFavoritesOompaLoompas: ErrorEntity? = null

            // Success data from getOompaLoompasUseCase
            if (oompaLoompas is Resource.Success) {
                oompaLoompasStateList = oompaLoompas.data.results.map { it.toOompaLoompaState() }
            }

            // Success data from getFavoritesOompaLoompasUseCase
            if (favoritesOompaLoompas is Resource.Success) {
                favoritesOompaLoompasStateList = favoritesOompaLoompas.data.results.map { it.toOompaLoompaState() }
            }

            // Error from getOompaLoompasUseCase or getFavoritesOompaLoompasUseCase
            if (oompaLoompas is Resource.Error || favoritesOompaLoompas is Resource.Error) {
                errorOompaLoompas = (oompaLoompas as Resource.Error).error
                errorFavoritesOompaLoompas = (favoritesOompaLoompas as Resource.Error).error
            }

            mutableUiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    oompaLoompas = WrappedStateList(oompaLoompasStateList),
                    favoritesOompaLoompas = WrappedStateList(favoritesOompaLoompasStateList),
                    errorOompaLoompas = errorOompaLoompas,
                    errorFavoritesOompaLoompas = errorFavoritesOompaLoompas,
                )
            }
        }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(7_000), initialValue = HomeScreenUiState()).launchIn(viewModelScope)
    }

    fun updateLastItemVisible(lastVisible: Int) {
        updateLoompasUseCase.execute(lastVisible).onStart { }.onEach { error ->
            mutableUiState.update { currentState ->
                currentState.copy(errorOompaLoompas = error)
            }
        }.onCompletion { }.launchIn(viewModelScope)
    }

    fun updateFavorite(id: Int) {
        postFavoriteUseCase.execute(id).onStart {}.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    mutableUiState.update { currentState ->
                        currentState.copy(errorPostfavorite = result.error)
                    }
                }
                is Resource.Success -> {
                    mutableUiState.update { currentState ->
                        currentState.copy(isSuccessPostfavorite = result.data)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUnfavorite(id: Int) {
        postUnfavoriteUseCase.execute(id).onStart { }.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    mutableUiState.update { currentState ->
                        currentState.copy(errorFavoritesOompaLoompas = result.error)
                    }
                }
                is Resource.Success -> {
                    mutableUiState.update { currentState ->
                        currentState.copy(isSuccessPostfavorite = result.data)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}