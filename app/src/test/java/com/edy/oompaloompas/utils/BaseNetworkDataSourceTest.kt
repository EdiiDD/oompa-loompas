package com.edy.oompaloompas.utils

import com.edy.oompaloompas.di.NetworkModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class BaseNetworkDataSourceTest {
    protected lateinit var mockWebServer: MockWebServer
    protected lateinit var mockURL: HttpUrl

    @Before
    open fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockURL = mockWebServer.url("/")
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }

    protected inline fun <reified T> getService(): T {
        return NetworkModule.provideRetrofit(
            okHttpClient = NetworkModule.providePublicHttpClient(
                NetworkModule.provideAddHeadersInterceptor(),
                NetworkModule.provideLoggingInterceptor(),
            ),
            url = mockURL,
            moshi = provideMoshi()
        ).create(T::class.java)
    }

    fun provideMoshi() : Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}
