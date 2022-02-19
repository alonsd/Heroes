package com.bankhapoalimheroes.data.repository

import com.bankhapoalimheroes.data.source.remote.source.RemoteHeroDetailsDataSource
import com.haroldadmin.cnradapter.NetworkResponse

class HeroesDetailsRepository(private val remoteHeroDetailsDataSource: RemoteHeroDetailsDataSource) {

    suspend fun getHeroDetails(heroId: String): NetworkResponse<*, String> =
        remoteHeroDetailsDataSource.getHeroDetails(heroId)

}