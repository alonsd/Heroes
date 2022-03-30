package com.heroes.data.source.remote.api

import com.heroes.model.network_models.HeroesListResponseModel
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroesDetailsApi {

    @GET("{accessToken}/{characterId}/powerstats")
    suspend fun getHeroPowerstats(
        @Path("accessToken") accessToken : String,
        @Path("characterId") characterId : String
    ) : NetworkResponse<HeroesListResponseModel.Hero.Powerstats, String>

    @GET("{accessToken}/{characterId}/biography")
    suspend fun getHeroBiography(
        @Path("accessToken") accessToken : String,
        @Path("characterId") characterId : String
    ) : NetworkResponse<HeroesListResponseModel.Hero.Biography, String>

    @GET("{accessToken}/{characterId}/appearance")
    suspend fun getHeroAppearance(
        @Path("accessToken") accessToken : String,
        @Path("characterId") characterId : String
    ) : NetworkResponse<HeroesListResponseModel.Hero.Appearance, String>

    @GET("{accessToken}/{characterId}/work")
    suspend fun getHeroWork(
        @Path("accessToken") accessToken : String,
        @Path("characterId") characterId : String
    ) : NetworkResponse<HeroesListResponseModel.Hero.Work, String>
}