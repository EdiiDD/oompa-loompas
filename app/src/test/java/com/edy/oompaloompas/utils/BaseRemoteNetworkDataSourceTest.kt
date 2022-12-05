package com.edy.oompaloompas.utils

import com.edy.oompaloompas.data.remote.constants.SERVICE_URL_BASE
import com.edy.oompaloompas.di.NetworkModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.After
import org.junit.Before

abstract class BaseRemoteNetworkDataSourceTest {
    protected lateinit var mockURL: String

    @Before
    open fun setUp() {
        mockURL = SERVICE_URL_BASE
    }

    @After
    open fun tearDown() {}

    protected inline fun <reified T> getService(): T {
        return NetworkModule.provideRetrofit(
            okHttpClient = NetworkModule.providePublicHttpClient(
                NetworkModule.provideAddHeadersInterceptor(),
                NetworkModule.provideLoggingInterceptor(),
            ),
            url = mockURL.toHttpUrl(),
            moshi = provideMoshi()
        ).create(T::class.java)
    }

    fun provideMoshi() : Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}
