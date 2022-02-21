package com.bankhapoalimheroes.data.repository

import com.bankhapoalimheroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSourceImp
import com.haroldadmin.cnradapter.NetworkResponse

class HeroesDetailsRepository(private val remoteHeroDetailsDataSourceImp: RemoteHeroDetailsDataSourceImp) {

    suspend fun getHeroDetails(heroId: String): NetworkResponse<*, String> =
        remoteHeroDetailsDataSourceImp.getHeroDetails(heroId)

}