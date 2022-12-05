package com.edy.oompaloompas.domain.interactors

import com.edy.oompaloompas.core.dispatcher.DispatcherProvider
import com.edy.oompaloompas.core.flow.FlowUseCase
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.domain.repositories.OompaLoompaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllProfessionsUseCase @Inject constructor(
    private val oompaLoompaRepository: OompaLoompaRepository,
    dispatcherProvider: DispatcherProvider,
) : FlowUseCase<Unit, Resource<List<String>>>(dispatcherProvider) {

    override fun start(param: Unit): Flow<Resource<List<String>>> = flow {
        emit(oompaLoompaRepository.getAllProfessions())
    }
}