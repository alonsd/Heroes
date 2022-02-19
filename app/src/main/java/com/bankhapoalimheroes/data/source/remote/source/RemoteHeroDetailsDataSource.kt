package com.bankhapoalimheroes.data.source.remote.source

import com.bankhapoalimheroes.data.source.remote.api.HeroesDetailsApi
import com.bankhapoalimheroes.model.network_models.HeroesListResponseModel
import com.bankhapoalimheroes.model.ui_models.hero_details.HeroDetailsModel
import com.bankhapoalimheroes.utils.constants.NetworkConstants
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class RemoteHeroDetailsDataSource(private val heroesDetailsApi: HeroesDetailsApi) {

    suspend fun getHeroDetails(heroId : String): NetworkResponse<*, String> {
        val heroAppearance = heroesDetailsApi.getHeroAppearance(NetworkConstants.TOKEN, heroId)
        val heroWork = heroesDetailsApi.getHeroWork(NetworkConstants.TOKEN, heroId)
        val heroBiography = heroesDetailsApi.getHeroBiography(NetworkConstants.TOKEN, heroId)
        val heroPowerstats = heroesDetailsApi.getHeroPowerstats(NetworkConstants.TOKEN, heroId)
        if (heroAppearance is NetworkResponse.Error) return heroAppearance
        if (heroWork is NetworkResponse.Error) return heroAppearance
        if (heroBiography is NetworkResponse.Error) return heroAppearance
        if (heroPowerstats is NetworkResponse.Error) return heroAppearance
        val heroDetails = HeroDetailsModel(
            (heroAppearance as NetworkResponse.Success).body,
            (heroWork as NetworkResponse.Success).body,
            (heroPowerstats as NetworkResponse.Success).body,
            (heroBiography as NetworkResponse.Success).body)
        return NetworkResponse.Success(heroDetails, code = 200)
    }

}