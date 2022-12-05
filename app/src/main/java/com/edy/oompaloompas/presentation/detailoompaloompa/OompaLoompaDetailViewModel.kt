package com.edy.oompaloompas.presentation.detailoompaloompa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.domain.interactors.GetOompaLoompaDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel
class OompaLoompaDetailViewModel @Inject constructor(
    private val getOompaLoompaDetailUseCase: GetOompaLoompaDetailUseCase,
) : ViewModel() {

    // UiState for exposed state
    private val mutableUiState = MutableStateFlow(DetailOompaLoompaScreenUiState())
    val uiState: StateFlow<DetailOompaLoompaScreenUiState> = mutableUiState.asStateFlow()


    fun initDetail(id: Int?) {
        id?.let {
            getOompaLoompaDetailUseCase
                .execute(id)
                .onStart { }
                .onEach { response ->
                    when (response) {
                        is Resource.Error -> mutableUiState.update { currentState ->
                            currentState.copy(
                                error = response.error,
                                isLoading = false
                            )
                        }
                        is Resource.Success -> mutableUiState.update { currentState ->
                            currentState.copy(
                                oompaLoompa = response.data.toOompaLoompaDetailState(),
                                isLoading = false
                            )
                        }
                    }
                }
                .launchIn(viewModelScope)
        } ?: run {
            mutableUiState.update { currentState ->
                currentState.copy(error = ErrorEntity.ApiError.NotFound)
            }
        }
    }

}