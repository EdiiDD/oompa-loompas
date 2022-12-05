package com.edy.oompaloompas.data.datasources

import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.data.models.OompaLoompaDetailDTO
import com.edy.oompaloompas.data.models.OompaLoompasDTO

interface OompaLoompasRemoteDataSource {

    suspend fun getOompaLoompas(page: Int): Resource<OompaLoompasDTO>

    suspend fun getOompaLoompasDetail(id: Int): Resource<OompaLoompaDetailDTO>
}