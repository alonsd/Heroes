package com.heroes.data.source.remote.source.hero_details

import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.data.source.remote.api.HeroesDetailsApi
import com.heroes.model.ui_models.hero_details.HeroDetailsCardModel
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.heroes.core.constants.NetworkConstants

class RemoteHeroDetailsDataSourceImp(private val heroesDetailsApi: HeroesDetailsApi) {


    suspend fun getHeroDetails(heroId: String): NetworkResponse<*, String> {
        val heroAppearance = heroesDetailsApi.getHeroAppearance(NetworkConstants.TOKEN, heroId)
        val heroWork = heroesDetailsApi.getHeroWork(NetworkConstants.TOKEN, heroId)
        val heroBiography = heroesDetailsApi.getHeroBiography(NetworkConstants.TOKEN, heroId)
        val heroPowerstats = heroesDetailsApi.getHeroPowerstats(NetworkConstants.TOKEN, heroId)
        if (heroAppearance is NetworkResponse.Error) return heroAppearance
        if (heroWork is NetworkResponse.Error) return heroAppearance
        if (heroBiography is NetworkResponse.Error) return heroAppearance
        if (heroPowerstats is NetworkResponse.Error) return heroAppearance
        val heroDetailsCards = mutableListOf<HeroDetailsCardModel>()
        heroDetailsCards.add(
            HeroDetailsCardModel(
                "Appearance", "Eye Color",
                (heroAppearance as NetworkResponse.Success).body.eyeColor
            )
        )
        heroDetailsCards.add(
            HeroDetailsCardModel(
                "Powerstats", "Durability",
                (heroPowerstats as NetworkResponse.Success).body.durability
            )
        )
        heroDetailsCards.add(
            HeroDetailsCardModel(
                "Work", "Base",
                (heroWork as NetworkResponse.Success).body.base
            )
        )

        val heroDetails = HeroDetailsModel((heroBiography as NetworkResponse.Success).body.placeOfBirth, heroDetailsCards)
        return NetworkResponse.Success(heroDetails, code = 200)
    }

}