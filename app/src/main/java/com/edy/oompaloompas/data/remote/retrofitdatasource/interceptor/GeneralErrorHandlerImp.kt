package com.edy.oompaloompas.data.remote.retrofitdatasource.interceptor

import com.edy.oompaloompas.core.error.ErrorEntity
import com.squareup.moshi.JsonDataException
import java.io.FileNotFoundException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import java.text.ParseException
import retrofit2.HttpException

fun Throwable.getError(): ErrorEntity {
    return when (this) {
        is HttpException -> {
            return when (this.code()) {
                // Client Error -> 40X
                HttpURLConnection.HTTP_BAD_REQUEST -> ErrorEntity.ApiError.BadRequest
                HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.ApiError.UnauthorizedError(this.message())
                HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.ApiError.NotFound

                // Client Error -> 50X
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> ErrorEntity.ApiError.TimeOutError
                HttpURLConnection.HTTP_UNAVAILABLE, HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.ApiError.ServiceUnavailable
                else -> ErrorEntity.Unknown
            }
        }
        is Exception -> {
            return when (this) {
                is UnknownHostException -> ErrorEntity.ApiError.ConnectivityError
                is ParseException -> ErrorEntity.ApiError.ParseError
                is FileNotFoundException -> ErrorEntity.FileError.NotFound
                is IOException -> ErrorEntity.FileError.ReadError
                is JsonDataException -> ErrorEntity.ApiError.ParseError
                else -> ErrorEntity.Unknown
            }
        }
        else -> ErrorEntity.Unknown
    }
}