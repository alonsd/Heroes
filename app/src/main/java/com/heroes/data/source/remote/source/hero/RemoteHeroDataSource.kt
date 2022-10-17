package com.heroes.data.source.remote.source.hero

import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.model.ui_models.heroes_list.HeroModel

interface RemoteHeroDataSource {

    suspend fun getHeroesByName(name: String): NetworkResponse<List<HeroModel>, String>

    suspend fun getSuggestedHeroesList(): NetworkResponse<List<HeroModel>, String>

}