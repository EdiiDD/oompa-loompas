package com.edy.oompaloompas.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.resourceFailure
import com.edy.oompaloompas.core.flow.resourceSuccess
import com.edy.oompaloompas.domain.interactors.GetFavoritesOompaLoompasWithLimitUseCase
import com.edy.oompaloompas.domain.interactors.GetOompaLoompasUseCase
import com.edy.oompaloompas.domain.interactors.PostFavoriteUseCase
import com.edy.oompaloompas.domain.interactors.PostUnfavoriteUseCase
import com.edy.oompaloompas.domain.interactors.UpdateLoompasUseCase
import com.edy.oompaloompas.domain.models.OompaLoompa
import com.edy.oompaloompas.domain.models.OompaLoompas
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @RelaxedMockK
    private lateinit var getOompaLoompasUseCase: GetOompaLoompasUseCase

    @RelaxedMockK
    private lateinit var getFavoritesOompaLoompasUseCase: GetFavoritesOompaLoompasWithLimitUseCase

    @RelaxedMockK
    private lateinit var postFavoriteUseCase: PostFavoriteUseCase

    @RelaxedMockK
    private lateinit var postUnfavoriteUseCase: PostUnfavoriteUseCase

    @RelaxedMockK
    private lateinit var updateLoompasUseCase: UpdateLoompasUseCase

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(Dispatchers.Unconfined)

        homeViewModel = HomeViewModel(
            getOompaLoompasUseCase,
            getFavoritesOompaLoompasUseCase,
            postFavoriteUseCase,
            postUnfavoriteUseCase,
            updateLoompasUseCase
        )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get all oompa loompas and get all favorites oompa loompas dont return error then the ui state dosent contains errors`() = runBlocking {

        // Mock response
        coEvery { getOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceSuccess(OompaLoompas(oompaLoompas)))

        coEvery { getFavoritesOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceSuccess(OompaLoompas(oompaLoompas)))

        coEvery { postFavoriteUseCase.execute(1) } returns flowOf(resourceSuccess(true))

        coEvery { postUnfavoriteUseCase.execute(1) } returns flowOf(resourceSuccess(true))

        coEvery { updateLoompasUseCase.execute(1) } returns flowOf()

        // OnInit ViewModel the uiState is the default by constructor
        coVerify(exactly = 0) { updateLoompasUseCase.execute(1) }
        coVerify(exactly = 1) { getOompaLoompasUseCase.execute(Unit) }
        coVerify(exactly = 1) { getFavoritesOompaLoompasUseCase.execute(Unit) }

        coVerify(exactly = 0) { postFavoriteUseCase.execute(1) }
        coVerify(exactly = 0) { postUnfavoriteUseCase.execute(1) }

        homeViewModel.uiState.test {
            val onInit = awaitItem()
            assertEquals(true, onInit.isLoading)
            assertEquals(false, onInit.isLoadingPagination)
            assertEquals(0, onInit.oompaLoompas.list.size)
            assertEquals(0, onInit.favoritesOompaLoompas.list.size)
            assertEquals(null, onInit.errorOompaLoompas)
            assertEquals(null, onInit.errorFavoritesOompaLoompas)
        }

        // After combine the result of getOompaLoompasUseCase and getFavoritesOompaLoompasUseCase
        homeViewModel.combineOompaLoompasAndFavorite()
        homeViewModel.uiState.test {
            val onInit = awaitItem()
            assertEquals(false, onInit.isLoading)
            assertEquals(false, onInit.isLoadingPagination)

            assertEquals(oompaLoompas.size, onInit.oompaLoompas.list.size)
            assertEquals(oompaLoompas[0].id, onInit.oompaLoompas.list[0].id)

            assertEquals(favoritesOompaLoompas.size, onInit.favoritesOompaLoompas.list.size)
            assertEquals(favoritesOompaLoompas[0].id, onInit.favoritesOompaLoompas.list[0].id)

            assertEquals(null, onInit.errorOompaLoompas)
            assertEquals(null, onInit.errorFavoritesOompaLoompas)
        }
    }


    @Test
    fun `when get all oompa loompas and get all favorites oompa loompas return error then the ui state contains errors`() = runBlocking {

        // Mock response
        coEvery { getOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceFailure(ErrorEntity.ApiError.ParseError))

        coEvery { getFavoritesOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceFailure(ErrorEntity.LocalDataBaseError.MissingLocalStorageError))

        coEvery { postFavoriteUseCase.execute(1) } returns flowOf(resourceSuccess(true))

        coEvery { postUnfavoriteUseCase.execute(1) } returns flowOf(resourceSuccess(true))

        coEvery { updateLoompasUseCase.execute(1) } returns flowOf()

        // OnInit ViewModel the uiState is the default by constructor
        coVerify(exactly = 0) { updateLoompasUseCase.execute(1) }
        coVerify(exactly = 1) { getOompaLoompasUseCase.execute(Unit) }
        coVerify(exactly = 1) { getFavoritesOompaLoompasUseCase.execute(Unit) }

        coVerify(exactly = 0) { postFavoriteUseCase.execute(1) }
        coVerify(exactly = 0) { postUnfavoriteUseCase.execute(1) }

        homeViewModel.uiState.test {
            val onInit = awaitItem()
            assertEquals(true, onInit.isLoading)
            assertEquals(false, onInit.isLoadingPagination)
            assertEquals(0, onInit.oompaLoompas.list.size)
            assertEquals(0, onInit.favoritesOompaLoompas.list.size)
            assertEquals(null, onInit.errorOompaLoompas)
            assertEquals(null, onInit.errorFavoritesOompaLoompas)
        }

        // After combine the result of getOompaLoompasUseCase and getFavoritesOompaLoompasUseCase
        homeViewModel.combineOompaLoompasAndFavorite()
        homeViewModel.uiState.test {
            val onInit = awaitItem()
            assertEquals(false, onInit.isLoading)
            assertEquals(false, onInit.isLoadingPagination)

            assertEquals(0, onInit.oompaLoompas.list.size)
            assertEquals(0, onInit.favoritesOompaLoompas.list.size)

            assertEquals(ErrorEntity.ApiError.ParseError, onInit.errorOompaLoompas)
            assertEquals(ErrorEntity.LocalDataBaseError.MissingLocalStorageError, onInit.errorFavoritesOompaLoompas)
        }
    }

    @Test
    fun `when post a favorite oompa loompa without error then the ui state contains errors`() = runBlocking {

        // Mock response
        coEvery { getOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceFailure(ErrorEntity.ApiError.ParseError))

        coEvery { getFavoritesOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceFailure(ErrorEntity.LocalDataBaseError.MissingLocalStorageError))

        coEvery { postFavoriteUseCase.execute(1) } returns flowOf(resourceSuccess(true))

        coEvery { postUnfavoriteUseCase.execute(1) } returns flowOf(resourceSuccess(true))

        coEvery { updateLoompasUseCase.execute(1) } returns flowOf()

        // OnInit ViewModel the uiState is the default by constructor
        coVerify(exactly = 0) { updateLoompasUseCase.execute(1) }
        coVerify(exactly = 1) { getOompaLoompasUseCase.execute(Unit) }
        coVerify(exactly = 1) { getFavoritesOompaLoompasUseCase.execute(Unit) }

        coVerify(exactly = 0) { postFavoriteUseCase.execute(1) }
        coVerify(exactly = 0) { postUnfavoriteUseCase.execute(1) }

        homeViewModel.uiState.test {
            val onInit = awaitItem()
            assertEquals(true, onInit.isLoading)
            assertEquals(false, onInit.isLoadingPagination)
            assertEquals(0, onInit.oompaLoompas.list.size)
            assertEquals(0, onInit.favoritesOompaLoompas.list.size)
            assertEquals(null, onInit.errorOompaLoompas)
            assertEquals(null, onInit.errorFavoritesOompaLoompas)
        }

        // Post favorite
        homeViewModel.updateFavorite(oompaLoompas[1].id)
        coVerify(exactly = 1) { postFavoriteUseCase.execute(oompaLoompas[1].id) }
        assertEquals(true, homeViewModel.uiState.value.isSuccessPostfavorite)
        assertEquals(null, homeViewModel.uiState.value.errorPostfavorite)
    }

    @Test
    fun `when post a favorite oompa loompa with error then the ui state contains errors`() = runBlocking {

        // Mock responses
        coEvery { getOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceSuccess(OompaLoompas(oompaLoompas)))

        coEvery { getFavoritesOompaLoompasUseCase.execute(Unit) } returns flowOf(resourceSuccess(OompaLoompas(favoritesOompaLoompas)))

        coEvery { postFavoriteUseCase.execute(1) } returns flowOf(resourceFailure(ErrorEntity.ApiError.ParseError))

        coEvery { postUnfavoriteUseCase.execute(1) } returns flowOf(resourceSuccess(true))

        coEvery { updateLoompasUseCase.execute(1) } returns flowOf()

        // OnInit ViewModel the uiState is the default by constructor
        coVerify(exactly = 0) { updateLoompasUseCase.execute(1) }
        coVerify(exactly = 1) { getOompaLoompasUseCase.execute(Unit) }
        coVerify(exactly = 1) { getFavoritesOompaLoompasUseCase.execute(Unit) }

        coVerify(exactly = 0) { postFavoriteUseCase.execute(1) }
        coVerify(exactly = 0) { postUnfavoriteUseCase.execute(1) }

        homeViewModel.uiState.test {
            val onInit = awaitItem()
            assertEquals(true, onInit.isLoading)
            assertEquals(false, onInit.isLoadingPagination)
            assertEquals(0, onInit.oompaLoompas.list.size)
            assertEquals(0, onInit.favoritesOompaLoompas.list.size)
            assertEquals(null, onInit.errorOompaLoompas)
            assertEquals(null, onInit.errorFavoritesOompaLoompas)
        }

        // Post favorite
        homeViewModel.updateFavorite(oompaLoompas[1].id)
        coVerify(exactly = 1) { postFavoriteUseCase.execute(oompaLoompas[1].id) }
        assertEquals(null, homeViewModel.uiState.value.isSuccessPostfavorite)
        assertEquals(ErrorEntity.ApiError.ParseError, homeViewModel.uiState.value.errorPostfavorite)
    }

    // Mock response getAllOompaLoompas
    private val oompaLoompas = MutableList(10) { index ->
        OompaLoompa(firstName = "Carlos",
            lastName = "Pepote",
            favoriteColor = "favoriteColor",
            favoriteFood = "favoriteFood",
            favoriteRandomString = "favoriteRandomString",
            favoriteSong = "favoriteSong",
            gender = "F",
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
            profession = "Medic",
            email = "email",
            age = 12,
            country = "country",
            height = 99,
            isFavorite = true,
            id = index)
    }

    // Mock response getFavoritesOompaLoompasUseCase
    private val favoritesOompaLoompas = MutableList(10) { index ->
        OompaLoompa(firstName = "Carlos",
            lastName = "Pepote",
            favoriteColor = "favoriteColor",
            favoriteFood = "favoriteFood",
            favoriteRandomString = "favoriteRandomString",
            favoriteSong = "favoriteSong",
            gender = "F",
            image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg",
            profession = "Medic",
            email = "email",
            age = 12,
            country = "country",
            height = 99,
            isFavorite = true,
            id = index)
    }

}