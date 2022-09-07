package com.heroes.data.source.remote.source.hero

import com.heroes.R
import com.heroes.data.source.remote.api.HeroesApi
import com.heroes.model.ui_models.heroes_list.BaseHeroModel
import com.heroes.model.ui_models.heroes_list.HeroSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroModel
import com.heroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType
import com.heroes.core.App
import com.heroes.core.constants.DefaultData
import com.heroes.core.constants.NetworkConstants
import com.heroes.core.constants.NetworkConstants.SUCCESS_RESULT_RESPONSE
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.core.constants.NetworkConstants.SUCCESS_RESULT_CODE
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class RemoteHeroDataSourceImp(private val heroesApi: HeroesApi) : RemoteHeroDataSource {


    override suspend fun getHeroesByNameWithSuggestions(name: String): NetworkResponse<*, String> {
        val suggestedHeroesResult = getSuggestedHeroesList(false)
        if (suggestedHeroesResult is NetworkResponse.Error) return suggestedHeroesResult
        val heroesResult = getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return heroesResult
        if ((heroesResult as NetworkResponse.Success).body.response != SUCCESS_RESULT_RESPONSE) {
            return NetworkResponse.Success(emptyList<BaseHeroModel>(), code = SUCCESS_RESULT_CODE)
        }
        val suggestedHeroesList = (suggestedHeroesResult as NetworkResponse.Success).body as List<HeroModel>
        val heroesListsWithSeparationModels = createHeroListWithSeparation(suggestedHeroesList)
        heroesResult.body.heroesList.forEach { hero ->
            val id = hero.id
            val imageUrl = hero.image.url
            val heroName = hero.name
            heroesListsWithSeparationModels.add(HeroModel(id, heroName, imageUrl))
        }
        return NetworkResponse.Success(heroesListsWithSeparationModels, code = SUCCESS_RESULT_CODE)
    }

    /**
     * @param addSeparation - We could use this function as the sole filler for the list or to get the
     * suggested heroes list combined with the search query results. If true, this means we are only showing the
     * recommended heroes and should add separation ViewHolders.
     */
    override suspend fun getSuggestedHeroesList(addSeparation: Boolean): NetworkResponse<*, String> {
        val suggestedHeroesList = mutableListOf<Deferred<NetworkResponse<*, String>>>()
        coroutineScope {
            DefaultData.SUGGESTED_HEROES_LIST.forEach { heroName ->
                val hero = async { getHeroesListContainingName(heroName) }
                suggestedHeroesList.add(hero)
            }
        }
        val suggestedHeroes = mutableListOf<HeroModel>()
        val awaitAll = suggestedHeroesList.awaitAll()
        awaitAll.forEach { networkResponse ->
            if (networkResponse is NetworkResponse.Error) return networkResponse
            val element = (networkResponse as NetworkResponse.Success).body as List<HeroModel>
            /*Response could be more then 1 hero when fetching data but we assume that
            when given an exact name we will get only one result and it will be correct.*/
            suggestedHeroes.add(element[0])
        }
        if (addSeparation.not())
            return NetworkResponse.Success(suggestedHeroes, code = SUCCESS_RESULT_CODE)
        val heroesListWithSeparation = createHeroListWithSeparation(suggestedHeroes)
        return NetworkResponse.Success(heroesListWithSeparation, code = SUCCESS_RESULT_CODE)
    }

    private suspend fun getHeroesByName(name: String) = heroesApi.getHeroesByName(NetworkConstants.TOKEN, name)

    private fun createHeroListWithSeparation(suggestedHeroesResult: List<HeroModel>): MutableList<BaseHeroModel> {
        val heroesListsModels = mutableListOf<BaseHeroModel>()
        heroesListsModels.add(0, HeroSeparatorModel(HeroesListSeparatorType.SUGGESTIONS))
        heroesListsModels.addAll(suggestedHeroesResult)
        heroesListsModels.add(HeroSeparatorModel(HeroesListSeparatorType.SEARCH))
        return heroesListsModels
    }

    private suspend fun getHeroesListContainingName(name: String): NetworkResponse<*, String> {
        val heroesResult = getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return heroesResult
        if ((heroesResult as NetworkResponse.Success).body.response != SUCCESS_RESULT_RESPONSE) {
            return NetworkResponse.UnknownError(Throwable(App.instance.getString(R.string.remote_hero_data_source_hero_not_found)))
        }
        val heroesListsModels = mutableListOf<HeroModel>()
        heroesResult.body.heroesList.forEach { hero ->
            val id = hero.id
            val imageUrl = hero.image.url
            val heroName = hero.name
            heroesListsModels.add(HeroModel(id, heroName, imageUrl))
        }
        return NetworkResponse.Success(heroesListsModels, code = SUCCESS_RESULT_CODE)
    }


}