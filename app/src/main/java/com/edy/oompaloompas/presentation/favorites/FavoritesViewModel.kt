package com.edy.oompaloompas.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.core.utils.WrappedStateList
import com.edy.oompaloompas.domain.interactors.GetFavoritesOompaLoompasUseCase
import com.edy.oompaloompas.domain.interactors.PostUnfavoriteUseCase
import com.edy.oompaloompas.presentation.home.toOompaLoompaState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesOompaLoompasUseCase: GetFavoritesOompaLoompasUseCase,
    private val postUnfavoriteUseCase: PostUnfavoriteUseCase,
) : ViewModel() {

    // UiState for exposed state
    private val mutableUiState = MutableStateFlow(FavoritesScreenUiState())
    val uiState: StateFlow<FavoritesScreenUiState> = mutableUiState.asStateFlow()

    init {
        getFavoritesOompaLoompasUseCase
            .execute(Unit)
            .onStart {
                mutableUiState.update { currentState ->
                    currentState.copy(isLoading = true)
                }
            }.onEach { response ->
                delay(1_000)
                when (response) {
                    is Resource.Error ->
                        mutableUiState.update { currentState ->
                            currentState.copy(error = response.error)
                        }
                    is Resource.Success ->
                        mutableUiState.update { currentState ->
                            currentState.copy(
                                favoritesOompaLoompas = WrappedStateList(response.data.results.map { it.toOompaLoompaState() }),
                                isLoading = false
                            )
                        }
                }
            }.launchIn(viewModelScope)

    }

    fun updateUnfavorite(id: Int) {
        postUnfavoriteUseCase
            .execute(id)
            .onStart { }
            .onEach { }
            .launchIn(viewModelScope)
    }
}