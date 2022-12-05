package com.edy.oompaloompas.data.local.roomsql

import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.core.flow.resourceFailure
import com.edy.oompaloompas.core.flow.resourceSuccess
import com.edy.oompaloompas.data.datasources.OompaLoompasLocalDataSource
import com.edy.oompaloompas.data.models.OompaLoompaLocalDTO
import com.edy.oompaloompas.data.models.OompaLoompasDTO
import com.edy.oompaloompas.data.remote.retrofitdatasource.interceptor.getError
import com.edy.oompaloompas.domain.interactors.Input
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class OompaLoompaLocalDataSourceImpl @Inject constructor(
    private val dataBase: IOompaLoompasDao,
) : OompaLoompasLocalDataSource {


    override fun getOompaLoompas(): Flow<List<OompaLoompaLocalDTO>> = dataBase.getAll()

    override fun getOompaLoompasWithFilter(filters: Input): Resource<List<OompaLoompaLocalDTO>> {
        return try {
            resourceSuccess(dataBase.getOompaLoompaWithFilter(initQuery(filters)))
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }

    override fun getFavoritesOompaLoompasWithLimit(): Flow<List<OompaLoompaLocalDTO>> = dataBase.getAllFavoritesWithLimit()

    override fun getFavoritesOompaLoompas(): Flow<List<OompaLoompaLocalDTO>> = dataBase.getAllFavorites()


    override suspend fun oompaLoompasCount(): Resource<Int> {
        return try {
            resourceSuccess(dataBase.oompaLoompasCount())
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }

    override suspend fun addManyOompaLoompas(oompaLoompas: List<OompaLoompaLocalDTO>): Resource<Int> {
        return try {
            dataBase.insertOompaLoompas(oompaLoompas)
            resourceSuccess(oompaLoompas.size)
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }

    override suspend fun addOneOompaLoompa(): Resource<Boolean> {
        return Resource.Success(true)
    }

    override suspend fun searchOompaLoompas(): Resource<OompaLoompasDTO> {
        return try {
            resourceSuccess(OompaLoompasDTO(
                current = 1,
                total = 2,
                results = emptyList()
            ))
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }

    override fun postFavoriteOompaLoompas(id: Int): Resource<Boolean> {
        return try {
            when (dataBase.postFavorite(id)) {
                in 0..Int.MAX_VALUE -> resourceSuccess(true)
                0 -> resourceSuccess(false)
                else -> resourceSuccess(false)
            }
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }

    override fun postUnfavoriteOompaLoompas(id: Int): Resource<Boolean> {
        return try {
            when (dataBase.postUnfavorite(id)) {
                in 0..Int.MAX_VALUE -> resourceSuccess(true)
                0 -> resourceSuccess(false)
                else -> resourceSuccess(false)
            }
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }

    override fun getAllProfessions(): Resource<List<String>> {
        return try {
            resourceSuccess(dataBase.getAllProfessions())
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }

    override fun getAllGenders(): Resource<List<String>> {
        return try {
            resourceSuccess(dataBase.getAllGenders())
        } catch (e: Exception) {
            resourceFailure(e.getError())
        }
    }
}