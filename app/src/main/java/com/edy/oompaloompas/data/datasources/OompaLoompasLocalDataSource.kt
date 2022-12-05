package com.edy.oompaloompas.data.datasources

import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.data.models.OompaLoompaLocalDTO
import com.edy.oompaloompas.data.models.OompaLoompasDTO
import com.edy.oompaloompas.domain.interactors.Input
import kotlinx.coroutines.flow.Flow

interface OompaLoompasLocalDataSource {

    fun getOompaLoompas(): Flow<List<OompaLoompaLocalDTO>>

    fun getOompaLoompasWithFilter(filters: Input): Resource<List<OompaLoompaLocalDTO>>

    fun getFavoritesOompaLoompasWithLimit(): Flow<List<OompaLoompaLocalDTO>>

    fun getFavoritesOompaLoompas(): Flow<List<OompaLoompaLocalDTO>>

    suspend fun oompaLoompasCount(): Resource<Int>

    suspend fun addManyOompaLoompas(oompaLoompas: List<OompaLoompaLocalDTO>): Resource<Int>

    suspend fun addOneOompaLoompa(): Resource<Boolean>

    suspend fun searchOompaLoompas(): Resource<OompaLoompasDTO>

    fun postFavoriteOompaLoompas(id: Int): Resource<Boolean>

    fun postUnfavoriteOompaLoompas(id: Int): Resource<Boolean>

    fun getAllProfessions(): Resource<List<String>>

    fun getAllGenders(): Resource<List<String>>
}