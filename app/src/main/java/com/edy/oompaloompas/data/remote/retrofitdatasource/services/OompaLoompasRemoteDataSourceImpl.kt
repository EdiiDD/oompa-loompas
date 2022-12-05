package com.edy.oompaloompas.data.remote.retrofitdatasource.services

import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.data.datasources.OompaLoompasRemoteDataSource
import com.edy.oompaloompas.data.models.OompaLoompaDetailDTO
import com.edy.oompaloompas.data.models.OompaLoompasDTO
import javax.inject.Inject

class OompaLoompasRemoteDataSourceImpl @Inject constructor(
    private val services: OompaLoompasServices
) : OompaLoompasRemoteDataSource {

    override suspend fun getOompaLoompas(page: Int): Resource<OompaLoompasDTO> = services.getOompaLoompas(page)

    override suspend fun getOompaLoompasDetail(id: Int): Resource<OompaLoompaDetailDTO> = services.getOompaLoompa(id)
}