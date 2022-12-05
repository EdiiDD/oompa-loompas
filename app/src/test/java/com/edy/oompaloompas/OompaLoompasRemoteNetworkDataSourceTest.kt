package com.edy.oompaloompas

import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.data.remote.retrofitdatasource.services.OompaLoompasServices
import com.edy.oompaloompas.utils.BaseRemoteNetworkDataSourceTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class OompaLoompasRemoteNetworkDataSourceTest : BaseRemoteNetworkDataSourceTest() {
    private lateinit var oompaLoompasServices: OompaLoompasServices

    @Before
    override fun setUp() {
        super.setUp()
        oompaLoompasServices = getService()
    }

    // Test directly with the server
    @Test
    fun addition_isCorrect() {
        runBlocking {
            val oompaLoompas = oompaLoompasServices.getOompaLoompas(1)
            assertEquals(true, oompaLoompas is Resource.Success)
        }
    }
}