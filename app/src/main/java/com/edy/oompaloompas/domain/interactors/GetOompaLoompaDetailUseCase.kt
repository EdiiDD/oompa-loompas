package com.edy.oompaloompas.domain.interactors

import com.edy.oompaloompas.core.dispatcher.DispatcherProvider
import com.edy.oompaloompas.core.flow.FlowUseCase
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.domain.models.OompaLoompaDetail
import com.edy.oompaloompas.domain.repositories.OompaLoompaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOompaLoompaDetailUseCase @Inject constructor(
    private val oompaLoompaRepository: OompaLoompaRepository,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Int, Resource<OompaLoompaDetail>>(dispatcherProvider) {

    override fun start(param: Int): Flow<Resource<OompaLoompaDetail>> = flow {
        emit(oompaLoompaRepository.getOompaLoopmaDetail(param))
    }
}