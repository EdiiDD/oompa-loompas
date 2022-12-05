package com.edy.oompaloompas.data.remote.retrofitdatasource.interceptor


import com.edy.oompaloompas.core.flow.Resource
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>,
) : Call<Resource<T>> {

    override fun enqueue(callback: Callback<Resource<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                handleApi { response }.also {
                    callback.onResponse(this@NetworkResultCall, Response.success(it))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Resource.Error<T>(error = t.getError()).also {
                    callback.onResponse(this@NetworkResultCall, Response.success(it))
                }
            }
        })
    }

    override fun execute(): Response<Resource<T>> = throw NotImplementedError()

    override fun clone(): Call<Resource<T>> = NetworkResultCall(proxy.clone())

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun cancel() {
        proxy.cancel()
    }
}