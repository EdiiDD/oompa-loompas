package com.edy.oompaloompas.core.error

sealed class ErrorEntity {

    object Unknown : ErrorEntity()

    sealed class ApiError : ErrorEntity() {
        data class UnauthorizedError(val error: String?) : ApiError()
        object ServiceUnavailable : ApiError()
        object ParseError : ApiError()
        object TimeOutError : ApiError()
        object ServerError : ApiError()
        object ConnectivityError : ApiError()
        object BadRequest : ApiError()
        object NotFound : ApiError()
    }

    sealed class FileError : ErrorEntity() {
        object NotFound : FileError()
        object ReadError : FileError()
    }

    sealed class LocalDataBaseError: ErrorEntity(){
        object MissingLocalStorageError : LocalDataBaseError()
    }
}