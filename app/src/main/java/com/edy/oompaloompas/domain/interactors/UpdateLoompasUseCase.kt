package com.edy.oompaloompas.domain.interactors

import com.edy.oompaloompas.core.dispatcher.DispatcherProvider
import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.FlowUseCase
import com.edy.oompaloompas.domain.repositories.OompaLoompaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UpdateLoompasUseCase @Inject constructor(
    private val oompaLoompaRepository: OompaLoompaRepository,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Int, ErrorEntity>(dispatcherProvider) {

    override fun start(param: Int): Flow<ErrorEntity> = oompaLoompaRepository.updateOompaLoopma(param)
}