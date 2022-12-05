package com.edy.oompaloompas.domain.repositories

import com.edy.oompaloompas.core.error.ErrorEntity
import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.domain.interactors.Input
import com.edy.oompaloompas.domain.models.OompaLoompaDetail
import com.edy.oompaloompas.domain.models.OompaLoompas
import kotlinx.coroutines.flow.Flow

interface OompaLoompaRepository {

    fun getOompaLoopma(): Flow<Resource<OompaLoompas>>

    fun getFavoritesOompaLoopmaWithLimit(): Flow<Resource<OompaLoompas>>

    fun getFavoritesOompaLoopma(): Flow<Resource<OompaLoompas>>

    fun getOompaLoopma(filters: Input): Resource<OompaLoompas>

    suspend fun getOompaLoopmaDetail(id: Int): Resource<OompaLoompaDetail>

    fun updateOompaLoopma(lastVisible: Int): Flow<ErrorEntity>

    fun postFavorite(id: Int): Resource<Boolean>

    fun postUnfavorite(id: Int): Resource<Boolean>

    fun getAllProfessions(): Resource<List<String>>

    fun getAllGenders(): Resource<List<String>>
}