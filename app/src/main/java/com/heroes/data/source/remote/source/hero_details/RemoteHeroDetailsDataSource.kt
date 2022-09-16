package com.heroes.data.source.remote.source.hero_details

import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.model.ui_models.hero_details.HeroDetailsModel

interface RemoteHeroDetailsDataSource {

    suspend fun getHeroDetails(heroId: String): NetworkResponse<HeroDetailsModel, String>
}