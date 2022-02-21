package com.bankhapoalimheroes.data.source.remote.source.hero_details

import com.haroldadmin.cnradapter.NetworkResponse

interface RemoteHeroDetailsDataSource {

    suspend fun getHeroDetails(heroId: String): NetworkResponse<*, String>
}