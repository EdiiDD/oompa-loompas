package com.edy.oompaloompas.core.flow

import com.edy.oompaloompas.core.error.ErrorEntity

sealed class Resource<T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<T>(val error: ErrorEntity): Resource<T>()
}

fun <T> resourceFailure(error: ErrorEntity) = Resource.Error<T>(error)
fun <T> resourceSuccess(data: T) = Resource.Success(data)
