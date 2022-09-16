package com.heroes.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.model.ui_models.heroes_list.HeroModel

interface HeroesRepository {

    suspend fun getHeroesByName(name: String) : NetworkResponse<List<HeroModel>, String>

    suspend fun getSuggestedHeroesList() : NetworkResponse<List<HeroModel>, String>
}