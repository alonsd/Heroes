package com.heroes.data.source.remote.source.hero_details

import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.data.source.remote.api.HeroesDetailsApi
import com.heroes.model.ui_models.hero_details.HeroDetailsCardModel
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.heroes.core.constants.NetworkConstants
import javax.inject.Inject

class RemoteHeroDetailsDataSourceImp @Inject constructor(
    private val heroesDetailsApi: HeroesDetailsApi
) : RemoteHeroDetailsDataSource {


    override suspend fun getHeroDetails(heroId: String): NetworkResponse<HeroDetailsModel, String> {
        val heroAppearance = heroesDetailsApi.getHeroAppearance(NetworkConstants.TOKEN, heroId)
        val heroWork = heroesDetailsApi.getHeroWork(NetworkConstants.TOKEN, heroId)
        val heroBiography = heroesDetailsApi.getHeroBiography(NetworkConstants.TOKEN, heroId)
        val heroPowerstats = heroesDetailsApi.getHeroPowerstats(NetworkConstants.TOKEN, heroId)
        if (heroAppearance is NetworkResponse.Error) return NetworkResponse.ServerError(
            code = 400,
            body = heroAppearance.error.localizedMessage ?: ""
        )
        if (heroWork is NetworkResponse.Error) return NetworkResponse.ServerError(
            code = 400,
            body = heroWork.error.localizedMessage ?: ""
        )
        if (heroBiography is NetworkResponse.Error) return NetworkResponse.ServerError(
            code = 400,
            body = heroBiography.error.localizedMessage ?: ""
        )
        if (heroPowerstats is NetworkResponse.Error) return NetworkResponse.ServerError(
            code = 400,
            body = heroPowerstats.error.localizedMessage ?: ""
        )
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