package com.bankhapoalimheroes.data.repository

import com.bankhapoalimheroes.data.source.local.source.LocalDataSource
import com.bankhapoalimheroes.data.source.remote.source.RemoteDataSource
import com.bankhapoalimheroes.model.ui_models.heroes_list.BaseHeroListModel
import com.bankhapoalimheroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.bankhapoalimheroes.model.ui_models.heroes_list.HeroesListModel
import com.bankhapoalimheroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType
import com.bankhapoalimheroes.utils.constants.DefaultData
import com.bankhapoalimheroes.utils.constants.NetworkConstants.SUCCESS_RESULT_CODE
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class HeroesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getHeroesByNameWithSuggestions(name: String): NetworkResponse<*, String> {
        val suggestedHeroesResult = getSuggestedHeroesList(false)
        if (suggestedHeroesResult is NetworkResponse.Error) return suggestedHeroesResult
        val heroesResult = remoteDataSource.getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return heroesResult
        val suggestedHeroesList = (suggestedHeroesResult as NetworkResponse.Success).body as List<HeroesListModel>
        val heroesListsWithSeparationModels = createHeroListWithSeparation(suggestedHeroesList)
        (heroesResult as NetworkResponse.Success).body.heroesList.forEach { hero ->
            val id = hero.id
            val imageUrl = hero.image.url
            val heroName = hero.name
            heroesListsWithSeparationModels.add(HeroesListModel(id, heroName, imageUrl))
        }
        return NetworkResponse.Success(heroesListsWithSeparationModels, code = SUCCESS_RESULT_CODE)
    }

    /**
     * @param addSeparation - We could use this function as the sole filler for the list or to get the
     * suggested heroes list combined with the search query results. If true, this means we are only showing the
     * recommended heroes and should add separation ViewHolders.
     */
    suspend fun getSuggestedHeroesList(addSeparation: Boolean): NetworkResponse<*, String> {
        val suggestedHeroesList = mutableListOf<Deferred<NetworkResponse<*, String>>>()
        coroutineScope {
            DefaultData.SUGGESTED_HEROES_LIST.forEach { heroName ->
                val hero = async { getHeroesListContainingName(heroName) }
                suggestedHeroesList.add(hero)
            }
        }
        val suggestedHeroes = mutableListOf<HeroesListModel>()
        suggestedHeroesList.awaitAll().forEach { networkResponse ->
            if (networkResponse is NetworkResponse.Error) return networkResponse
            val element = (networkResponse as NetworkResponse.Success).body as List<HeroesListModel>
            /*Response could be more then 1 hero when fetching data but we assume that
            when given an exact name we will get only one result and it will be correct.*/
            suggestedHeroes.add(element[0])
        }
        if (addSeparation.not())
            return NetworkResponse.Success(suggestedHeroes, code = SUCCESS_RESULT_CODE)
        val heroesListWithSeparation = createHeroListWithSeparation(suggestedHeroes)
        return NetworkResponse.Success(heroesListWithSeparation, code = SUCCESS_RESULT_CODE)
    }

    private fun createHeroListWithSeparation(suggestedHeroesResult: List<HeroesListModel>): MutableList<BaseHeroListModel> {
        val heroesListsModels = mutableListOf<BaseHeroListModel>()
        heroesListsModels.add(0, HeroListSeparatorModel(HeroesListSeparatorType.SUGGESTIONS))
        heroesListsModels.addAll(suggestedHeroesResult)
        heroesListsModels.add(HeroListSeparatorModel(HeroesListSeparatorType.SEARCH))
        return heroesListsModels
    }

    private suspend fun getHeroesListContainingName(name: String): NetworkResponse<*, String> {
        val heroesResult = remoteDataSource.getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return heroesResult
        val heroesListsModels = mutableListOf<HeroesListModel>()
        (heroesResult as NetworkResponse.Success).body.heroesList.forEach { hero ->
            val id = hero.id
            val imageUrl = hero.image.url
            val heroName = hero.name
            heroesListsModels.add(HeroesListModel(id, heroName, imageUrl))
        }
        return NetworkResponse.Success(heroesListsModels, code = SUCCESS_RESULT_CODE)
    }

    suspend fun getDataFromLocal() = localDataSource.getBasicApplicationModel()

}

