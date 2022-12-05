package com.edy.oompaloompas.data.remote.retrofitdatasource.services

import com.edy.oompaloompas.core.flow.Resource
import com.edy.oompaloompas.data.models.OompaLoompaDetailDTO
import com.edy.oompaloompas.data.models.OompaLoompasDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OompaLoompasServices {

    @GET("oompa-loompas")
    suspend fun getOompaLoompas(@Query("page") page: Int): Resource<OompaLoompasDTO>

    @GET("oompa-loompas/{id}")
    suspend fun getOompaLoompa(@Path("id") idOompaLoompa: Int): Resource<OompaLoompaDetailDTO>
}