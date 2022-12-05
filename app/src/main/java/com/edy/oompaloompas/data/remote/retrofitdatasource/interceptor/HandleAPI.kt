package com.edy.oompaloompas.data.remote.retrofitdatasource.interceptor

import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.Resource
import retrofit2.Response

fun <T : Any> handleApi(
    execute: () -> Response<T>,
): Resource<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Resource.Success(body)
        } else {
            Resource.Error(error = ErrorEntity.Unknown)
        }
    } catch (e: Exception) {
        Resource.Error(error = e.getError())
    }
}