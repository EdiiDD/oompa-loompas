package com.edy.oompaloompas.data.repositories

import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.data.datasources.OompaLoompasLocalDataSource
import com.edy.oompaloompas.data.datasources.OompaLoompasRemoteDataSource
import com.edy.oompaloompas.data.mappers.toOompaLoompa
import com.edy.oompaloompas.data.models.FavoriteOompaLoompaDTO
import com.edy.oompaloompas.data.models.OompaLoompaDTO
import com.edy.oompaloompas.data.models.OompaLoompasDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OompaLoompaRepositoryImplTest {


    @RelaxedMockK
    private lateinit var remoteDataSource: OompaLoompasRemoteDataSource

    @RelaxedMockK
    private lateinit var localDataSource: OompaLoompasLocalDataSource

    private lateinit var oompaLoompaRepository: OompaLoompaRepositoryImpl

    private val responseLocal = listOf(
        OompaLoompaDTO(
            firstName = "Marcy",
            lastName = "Karadzas",
            favorite = FavoriteOompaLoompaDTO(
                color = "red",
                food = "Chocolat",
                randomString = "random_string",
                song = "Oompa"
            ),
            gender = "F",
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg",
            profession = "Developer",
            email = "mkaradzas1@visualengin.com",
            age = 21,
            country = "Loompalandia",
            height = 99,
            id = 1
        ),
        OompaLoompaDTO(
            firstName = "Marcy",
            lastName = "Karadzas",
            favorite = FavoriteOompaLoompaDTO(
                color = "red",
                food = "Chocolat",
                randomString = "random_string",
                song = "Oompa"
            ),
            gender = "F",
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg",
            profession = "Developer",
            email = "mkaradzas1@visualengin.com",
            age = 21,
            country = "Loompalandia",
            height = 99,
            id = 2
        )
    )

    private val responseRemote = listOf(
        OompaLoompaDTO(
            firstName = "Marcy",
            lastName = "Karadzas",
            favorite = FavoriteOompaLoompaDTO(
                color = "red",
                food = "Chocolat",
                randomString = "random_string",
                song = "Oompa"
            ),
            gender = "F",
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg",
            profession = "Developer",
            email = "mkaradzas1@visualengin.com",
            age = 21,
            country = "Loompalandia",
            height = 99,
            id = 1
        ),
        OompaLoompaDTO(
            firstName = "Marcy",
            lastName = "Karadzas",
            favorite = FavoriteOompaLoompaDTO(
                color = "red",
                food = "Chocolat",
                randomString = "random_string",
                song = "Oompa"
            ),
            gender = "F",
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg",
            profession = "Developer",
            email = "mkaradzas1@visualengin.com",
            age = 21,
            country = "Loompalandia",
            height = 99,
            id = 2
        )
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        oompaLoompaRepository = OompaLoompaRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `when call to next page`() = runBlocking {
        //assertEquals(1, oompaLoompaRepository.getPage(0))
        assertEquals(true, oompaLoompaRepository.isNextPage(0, 0))
        assertEquals(false, oompaLoompaRepository.isNextPage(1, 20))
        assertEquals(true, oompaLoompaRepository.isNextPage(17, 20))
        assertEquals(false, oompaLoompaRepository.isNextPage(18, 40))
        assertEquals(true, oompaLoompaRepository.isNextPage(37, 40))
        assertEquals(false, oompaLoompaRepository.isNextPage(38, 60))
        assertEquals(true, oompaLoompaRepository.isNextPage(57, 60))
        assertEquals(false, oompaLoompaRepository.isNextPage(58, 80))
    }

    @Test
    fun `calculate next page`() = runBlocking {
        assertEquals(1, oompaLoompaRepository.getPage(0))
        assertEquals(2, oompaLoompaRepository.getPage(20))
        assertEquals(2, oompaLoompaRepository.getPage(30))
        assertEquals(3, oompaLoompaRepository.getPage(40))
        assertEquals(3, oompaLoompaRepository.getPage(50))
        assertEquals(4, oompaLoompaRepository.getPage(60))
        assertEquals(4, oompaLoompaRepository.getPage(70))
    }


    @Test
    fun `when last visible item is 0 than call API`() = runBlocking {
        val oompaLoompaslocalDTO = responseRemote.map { it.toOompaLoompa() }

        val oompaLoompasCount = 0
        val lastVisible = 0

        val page = oompaLoompaRepository.getPage(oompaLoompasCount)

        coEvery { localDataSource.oompaLoompasCount() } returns Resource.Success(oompaLoompasCount)

        coEvery { remoteDataSource.getOompaLoompas(page) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseRemote
            ))

        val response = oompaLoompaRepository.updateOompaLoopma(lastVisible).count()
        coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(page) }
        coVerify(exactly = 1) { localDataSource.addManyOompaLoompas(oompaLoompaslocalDTO) }
        assertEquals(0, response)
        assertEquals(oompaLoompaslocalDTO[0].id, responseRemote[0].id)
    }

    @Test
    fun `when last visible item is 17 then call API`() = runBlocking {
        val oompaLoompaslocalDTO = responseRemote.map { it.toOompaLoompa() }

        val oompaLoompasCount = 20
        val lastVisible = 17

        val page = oompaLoompaRepository.getPage(oompaLoompasCount)

        coEvery { localDataSource.oompaLoompasCount() } returns Resource.Success(oompaLoompasCount)

        coEvery { remoteDataSource.getOompaLoompas(page) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseRemote
            ))

        val response = oompaLoompaRepository.updateOompaLoopma(lastVisible).count()
        coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(page) }
        coVerify(exactly = 1) { localDataSource.addManyOompaLoompas(oompaLoompaslocalDTO) }
        assertEquals(0, response)
        assertEquals(2, page)
        assertEquals(oompaLoompaslocalDTO[0].id, responseRemote[0].id)
    }

    @Test
    fun `when last visible item is 18 then call API`() = runBlocking {
        val oompaLoompaslocalDTO = responseRemote.map { it.toOompaLoompa() }

        val oompaLoompasCount = 40
        val lastVisible = 18

        val page = oompaLoompaRepository.getPage(oompaLoompasCount)

        coEvery { localDataSource.oompaLoompasCount() } returns Resource.Success(oompaLoompasCount)

        coEvery { remoteDataSource.getOompaLoompas(page) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseRemote
            ))

        val response = oompaLoompaRepository.updateOompaLoopma(lastVisible).count()
        coVerify(exactly = 0) { remoteDataSource.getOompaLoompas(page) }
        coVerify(exactly = 0) { localDataSource.addManyOompaLoompas(oompaLoompaslocalDTO) }
        assertEquals(0, response)
        assertEquals(oompaLoompaslocalDTO[0].id, responseRemote[0].id)
    }

    @Test
    fun `when last visible item is 20 then call API`() = runBlocking {
        val oompaLoompaslocalDTO = responseRemote.map { it.toOompaLoompa() }

        val oompaLoompasCount = 20
        val lastVisible = 20

        val page = oompaLoompaRepository.getPage(oompaLoompasCount)

        coEvery { localDataSource.oompaLoompasCount() } returns Resource.Success(oompaLoompasCount)

        coEvery { remoteDataSource.getOompaLoompas(page) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseRemote
            ))

        val response = oompaLoompaRepository.updateOompaLoopma(lastVisible).count()

        coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(page) }
        coVerify(exactly = 1) { localDataSource.addManyOompaLoompas(oompaLoompaslocalDTO) }
        assertEquals(0, response)
        assertEquals(2, page)
        assertEquals(oompaLoompaslocalDTO[0].id, responseRemote[0].id)
    }

    @Test
    fun `when last visible item is 21 then call API`() = runBlocking {
        val oompaLoompaslocalDTO = responseRemote.map { it.toOompaLoompa() }

        val oompaLoompasCount = 40
        val lastVisible = 21

        val page = oompaLoompaRepository.getPage(oompaLoompasCount)

        coEvery { localDataSource.oompaLoompasCount() } returns Resource.Success(oompaLoompasCount)

        coEvery { remoteDataSource.getOompaLoompas(page) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseRemote
            ))

        val response = oompaLoompaRepository.updateOompaLoopma(lastVisible).count()

        coVerify(exactly = 0) { remoteDataSource.getOompaLoompas(page) }
        coVerify(exactly = 0) { localDataSource.addManyOompaLoompas(oompaLoompaslocalDTO) }
        assertEquals(0, response)
        assertEquals(3, page)
        assertEquals(oompaLoompaslocalDTO[0].id, responseRemote[0].id)
    }

    @Test
    fun `when last visible item is 0 and the API return error`() = runBlocking {
        val oompaLoompasCount = 0
        val oompaLoompaslocalDTO = responseRemote.map { it.toOompaLoompa() }

        coEvery { localDataSource.oompaLoompasCount() } returns Resource.Success(oompaLoompasCount)

        coEvery { remoteDataSource.getOompaLoompas(1) } returns Resource.Error(ErrorEntity.ApiError.ParseError)

        val response = oompaLoompaRepository.updateOompaLoopma(21).first()
        coVerify(exactly = 1) { localDataSource.oompaLoompasCount() }
        coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(1) }
        coVerify(exactly = 0) { localDataSource.addManyOompaLoompas(oompaLoompaslocalDTO) }
        assertEquals(ErrorEntity.ApiError.ParseError, response)

    }


    /* @Test
    fun `when the local data base return data then dont call the API and return data from data base`() = runBlocking {

        coEvery { remoteDataSource.getOompaLoompas(1) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseRemote

            ))

        *//*coEvery { localDataSource.getOompaLoompas() } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseLocal
            ))*//*


        oompaLoompaRepository.getOompaLoopma().collect {
            coVerify(exactly = 1) { localDataSource.getOompaLoompas() }
            coVerify(exactly = 0) { remoteDataSource.getOompaLoompas(1) }
            coVerify(exactly = 0) { localDataSource.addManyOompaLoompas(emptyList()) }
            assertEquals(true, it is Resource.Success)
            with(it as Resource.Success) {
                assertEquals(responseLocal[0].id, data.results[0].id)
            }
        }

    }


    @Test
    fun `when the local data base dosent return anything and get success data from API then return data from API`() = runBlocking {

        coEvery { remoteDataSource.getOompaLoompas(1) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseLocal
            ))

        *//* coEvery { localDataSource.getOompaLoompas() } returns Resource.Success(
             data = OompaLoompasDTO(
                 current = 1,
                 total = 20,
                 results = emptyList()

             ))*//*


        oompaLoompaRepository.getOompaLoopma().collect {
            coVerify(exactly = 1) { localDataSource.getOompaLoompas() }
            coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(1) }
            coVerify(exactly = 1) { localDataSource.addManyOompaLoompas(emptyList()) }
            assertEquals(true, it is Resource.Success)
            with(it as Resource.Success) {
                assertEquals(responseRemote[0].id, data.results[0].id)
            }
        }
    }

    @Test
    fun `when the local data base dosent return anything and get empty data from API then return empty data`() = runBlocking {

        coEvery { remoteDataSource.getOompaLoompas(1) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = emptyList()
            ))

        *//* coEvery { localDataSource.getOompaLoompas() } returns Resource.Success(
             data = OompaLoompasDTO(
                 current = 1,
                 total = 20,
                 results = emptyList()

             ))*//*


        oompaLoompaRepository.getOompaLoopma().collect {
            coVerify(exactly = 1) { localDataSource.getOompaLoompas() }
            coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(1) }
            coVerify(exactly = 0) { localDataSource.addManyOompaLoompas(emptyList()) }
            assertEquals(true, it is Resource.Success)
            with(it as Resource.Success) {
                assertEquals(0, data.results.size)

            }
        }
    }

    @Test
    fun `when the local data base return error and get succes data from API then return data from API`() = runBlocking {

        coEvery { remoteDataSource.getOompaLoompas(1) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = responseRemote
            ))

        //coEvery { localDataSource.getOompaLoompas() } returns Resource.Error(ErrorEntity.Unknown)

        oompaLoompaRepository.getOompaLoopma().collect {
            coVerify(exactly = 1) { localDataSource.getOompaLoompas() }
            coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(1) }
            coVerify(exactly = 1) { localDataSource.addManyOompaLoompas(emptyList()) }
            assertEquals(true, it is Resource.Success)
            with(it as Resource.Success) {
                assertEquals(responseRemote[0].id, data.results[0].id)
            }
        }
    }

    @Test
    fun `when the local data base return error and get empty data from API then return data from API`() = runBlocking {

        coEvery { remoteDataSource.getOompaLoompas(1) } returns Resource.Success(
            data = OompaLoompasDTO(
                current = 1,
                total = 20,
                results = emptyList()
            ))

        //coEvery { localDataSource.getOompaLoompas() } returns Resource.Error(ErrorEntity.Unknown)

        oompaLoompaRepository.getOompaLoopma().collect {
            coVerify(exactly = 1) { localDataSource.getOompaLoompas() }
            coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(1) }
            coVerify(exactly = 1) { localDataSource.addManyOompaLoompas(emptyList()) }
            assertEquals(true, it is Resource.Success)
            with(it as Resource.Success) {
                assertEquals(0, data.results.size)
            }
        }
    }

    @Test
    fun `when the local data base return error and get error from API then return error from API`() = runBlocking {

        coEvery { remoteDataSource.getOompaLoompas(1) } returns Resource.Error(ErrorEntity.ApiError.ServerError)

        //coEvery { localDataSource.getOompaLoompas() } returns Resource.Error(ErrorEntity.LocalDataBaseError.MissingLocalStorageError)


        oompaLoompaRepository.getOompaLoopma().collect {
            coVerify(exactly = 1) { localDataSource.getOompaLoompas() }
            coVerify(exactly = 1) { remoteDataSource.getOompaLoompas(1) }
            coVerify(exactly = 0) { localDataSource.addManyOompaLoompas(emptyList()) }
            assertEquals(true, it is Resource.Error)
            with(it as Resource.Error) {
                assertEquals(ErrorEntity.ApiError.ServerError, error)
            }
        }
    }*/
}

