package com.heroes.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSource
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import javax.inject.Inject

class HeroesDetailsRepositoryImpl @Inject constructor(
    private val remoteHeroDetailsDataSource: RemoteHeroDetailsDataSource
) : HeroesDetailsRepository {

    override suspend fun getHeroDetails(heroId: String): NetworkResponse<HeroDetailsModel, String> =
        remoteHeroDetailsDataSource.getHeroDetails(heroId)

}