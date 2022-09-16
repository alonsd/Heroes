package com.heroes.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.model.ui_models.hero_details.HeroDetailsModel

interface HeroesDetailsRepository {

    suspend fun getHeroDetails(heroId: String): NetworkResponse<HeroDetailsModel, String>
}