package com.edy.oompaloompas.domain.interactors

import com.edy.oompaloompas.core.dispatcher.DispatcherProvider
import com.edy.oompaloompas.core.flow.FlowUseCase
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.domain.models.OompaLoompas
import com.edy.oompaloompas.domain.repositories.OompaLoompaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFilterOompaLoompasUseCase @Inject constructor(
    private val oompaLoompaRepository: OompaLoompaRepository,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Input, Resource<OompaLoompas>>(dispatcherProvider) {

    override fun start(param: Input): Flow<Resource<OompaLoompas>> = flow {
        emit(oompaLoompaRepository.getOompaLoopma(param))
    }
}

// Class for differents filters
data class Input(
    val firstName: String?,
    val gender: String?,
    val profession: String?,
)