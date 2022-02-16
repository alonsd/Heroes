package com.bankhapoalimheroes.data.repository

import com.bankhapoalimheroes.data.source.local.source.LocalDataSource
import com.bankhapoalimheroes.data.source.remote.source.RemoteDataSource
import com.bankhapoalimheroes.model.ui_models.HeroesListModel
import com.haroldadmin.cnradapter.NetworkResponse

class HeroesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getHeroesListByName(name: String): NetworkResponse<*, String> {
        val heroesResult = remoteDataSource.getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return heroesResult
        val heroesListsModels = mutableListOf<HeroesListModel>()
        (heroesResult as NetworkResponse.Success).body.heroesList.forEach { hero ->
            val id = hero.id
            val imageUrl = hero.image.url
            val heroName = hero.name
            heroesListsModels.add(HeroesListModel(id, heroName, imageUrl))
        }
        return NetworkResponse.Success(heroesListsModels, code = 200)
    }

    suspend fun getDataFromLocal() = localDataSource.getBasicApplicationModel()

}

