package com.heroes.data.repository

import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSourceImp
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.model.ui_models.hero_details.HeroDetailsModel

class HeroesDetailsRepositoryImpl(
    private val remoteHeroDetailsDataSourceImp: RemoteHeroDetailsDataSourceImp
) : HeroesDetailsRepository {

    override suspend fun getHeroDetails(heroId: String): NetworkResponse<HeroDetailsModel, String> =
        remoteHeroDetailsDataSourceImp.getHeroDetails(heroId)

}