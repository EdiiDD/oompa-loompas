package com.edy.oompaloompas.data.repositories

import androidx.annotation.VisibleForTesting
import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.core.flow.resourceFailure
import com.edy.oompaloompas.core.flow.resourceSuccess
import com.edy.oompaloompas.data.datasources.OompaLoompasLocalDataSource
import com.edy.oompaloompas.data.datasources.OompaLoompasRemoteDataSource
import com.edy.oompaloompas.data.mappers.toOompaLoompa
import com.edy.oompaloompas.data.mappers.toOompaLoompaDetail
import com.edy.oompaloompas.domain.interactors.Input
import com.edy.oompaloompas.domain.models.OompaLoompaDetail
import com.edy.oompaloompas.domain.models.OompaLoompas
import com.edy.oompaloompas.domain.repositories.OompaLoompaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class OompaLoompaRepositoryImpl @Inject constructor(
    private val remoteDatasource: OompaLoompasRemoteDataSource,
    private val localDataSource: OompaLoompasLocalDataSource,
) : OompaLoompaRepository {

    companion object {
        private const val PAGE_SIZE = 20
        private const val PAGE_THRESHOLD = 3
    }

    // Get all oompas loompas from local database -> Reactive
    override fun getOompaLoopma(): Flow<Resource<OompaLoompas>> = localDataSource.getOompaLoompas()
        .map {
            Resource.Success(OompaLoompas(it.map { it.toOompaLoompa() }))
        }

    // Get all favorites oompas loompas with limit from local database -> Reactive
    override fun getFavoritesOompaLoopmaWithLimit(): Flow<Resource<OompaLoompas>> = localDataSource.getFavoritesOompaLoompasWithLimit()
        .map {
            Resource.Success(OompaLoompas(it.map { it.toOompaLoompa() }))
        }

    // Get all favorites oompas loompas from local database -> Reactive
    override fun getFavoritesOompaLoopma(): Flow<Resource<OompaLoompas>> = localDataSource.getFavoritesOompaLoompas()
        .map {
            Resource.Success(OompaLoompas(it.map { it.toOompaLoompa() }))
        }

    // Get all oompas lumpas with filter(first name, age, profession) from local databse
    override fun getOompaLoopma(filters: Input): Resource<OompaLoompas> {
        return when (val response = localDataSource.getOompaLoompasWithFilter(filters)) {
            is Resource.Error -> resourceFailure(response.error)
            is Resource.Success -> resourceSuccess(OompaLoompas(response.data.map { it.toOompaLoompa() }))
        }
    }

    // Get all detail oompa loompa from remote service
    override suspend fun getOompaLoopmaDetail(id: Int): Resource<OompaLoompaDetail> {
        return when (val response = remoteDatasource.getOompaLoompasDetail(id)) {
            is Resource.Error -> resourceFailure(response.error)
            is Resource.Success -> resourceSuccess(response.data.toOompaLoompaDetail())
        }
    }

    // Update the local database with new oompa loompas
    override fun updateOompaLoopma(lastVisible: Int): Flow<ErrorEntity> = flow {
        // Get data from local database
        when (val size = localDataSource.oompaLoompasCount()) {
            is Resource.Error -> emit(size.error)
            is Resource.Success -> {
                if (isNextPage(lastVisible, size.data)) {
                    val page = getPage(size.data)
                    when (val remoteOompaLoompas = remoteDatasource.getOompaLoompas(page)) {
                        is Resource.Error -> emit(remoteOompaLoompas.error)
                        // If the respose is correct update the local database with the result
                        is Resource.Success -> localDataSource.addManyOompaLoompas(remoteOompaLoompas.data.results.map { it.toOompaLoompa() })
                    }
                }
            }
        }
    }

    // Update oompa loompa to favorite
    override fun postFavorite(id: Int): Resource<Boolean> = localDataSource.postFavoriteOompaLoompas(id)

    // Update oompa loompa to not favorite
    override fun postUnfavorite(id: Int): Resource<Boolean> = localDataSource.postUnfavoriteOompaLoompas(id)

    // Get all  oompa loompa's profession
    override fun getAllProfessions(): Resource<List<String>> = localDataSource.getAllProfessions()

    // Get all  oompa loompa's genders
    override fun getAllGenders(): Resource<List<String>> = localDataSource.getAllGenders()

    // Calculate the page for the request to the remote service
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun getPage(size: Int): Int = size / PAGE_SIZE + 1

    // Calculate the if the last visible item is almost to the end
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun isNextPage(lastVisible: Int, size: Int): Boolean = lastVisible >= size - PAGE_THRESHOLD

    /* Without Room flow
     override suspend fun getOompaLoopma(): Flow<Resource<OompaLoompas>> = flow {
        emit(when (val loompaLoompasLocal = localDataSource.getOompaLoompas()) {

            // Local database doesnt return anything or return error
            is Resource.Error -> {

                //Call to the API for data
                when (val loompaLoompasRemote = remoteDatasource.getOompaLoompas()) {

                    // If the API return error
                    is Resource.Error -> resourceFailure(loompaLoompasRemote.error)

                    // If the API return data then persist
                    is Resource.Success -> {
                        localDataSource.addManyOompaLoompas()
                        resourceSuccess(loompaLoompasRemote.data.toOompaLoompas())
                    }
                }
            }

            // Local database return data
            is Resource.Success -> {
                when (loompaLoompasLocal.data.results.isEmpty()) {

                    // If the local databse return empty data
                    true -> {

                        //Call to the API for data
                        when (val loompaLoompasRemote = remoteDatasource.getOompaLoompas()) {
                            // If the API return empty data
                            is Resource.Error -> resourceFailure(loompaLoompasRemote.error)

                            // If the API return data then persist
                            is Resource.Success -> {
                                when (loompaLoompasRemote.data.results.isEmpty()) {
                                    true -> resourceSuccess(OompaLoompas(emptyList()))
                                    false -> {
                                        localDataSource.addManyOompaLoompas()
                                        resourceSuccess(loompaLoompasRemote.data.toOompaLoompas())
                                    }
                                }
                            }
                        }
                    }

                    // If the API return data then persist
                    false -> {
                        resourceSuccess(loompaLoompasLocal.data.toOompaLoompas())
                    }
                }
            }
        })
    }*/
}