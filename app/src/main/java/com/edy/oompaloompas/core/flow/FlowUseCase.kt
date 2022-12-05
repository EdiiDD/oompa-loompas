package com.edy.oompaloompas.core.flow

import com.edy.oompaloompas.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<T, R>(protected open val dispatcherProvider: DispatcherProvider) {

    protected open fun dispatcher(): CoroutineDispatcher = dispatcherProvider.io()

    protected abstract fun start(param: T): Flow<R>

    fun execute(param: T) = start(param)
        .flowOn(dispatcher())
}
