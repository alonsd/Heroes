package com.heroes.data.source.remote.api

import com.heroes.model.network_models.HeroesListResponseModel
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroesApi {

    @GET("{accessToken}/search/{name}")
    suspend fun getHeroesByName(
        @Path("accessToken") accessToken : String,
        @Path("name") name : String,
    ): NetworkResponse<HeroesListResponseModel, String>
}