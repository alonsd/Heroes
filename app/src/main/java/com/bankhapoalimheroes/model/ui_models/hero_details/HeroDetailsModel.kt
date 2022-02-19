package com.bankhapoalimheroes.model.ui_models.hero_details

import com.bankhapoalimheroes.model.network_models.HeroesListResponseModel

data class HeroDetailsModel (
    val appearance : HeroesListResponseModel.Hero.Appearance,
    val work : HeroesListResponseModel.Hero.Work,
    val powerstats : HeroesListResponseModel.Hero.Powerstats,
    val biography : HeroesListResponseModel.Hero.Biography
)