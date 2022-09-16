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
//        val suggestedHeroesResult = getSuggestedHeroesList()
//        if (suggestedHeroesResult is NetworkResponse.Error) return suggestedHeroesResult
        val heroesResult = getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return heroesResult
        if ((heroesResult as NetworkResponse.Success).body.response != SUCCESS_RESULT_RESPONSE) {
            return NetworkResponse.Success(emptyList<BaseHeroModel>(), code = SUCCESS_RESULT_CODE)
        }
        return NetworkResponse.Success(heroesResult, code = SUCCESS_RESULT_CODE)
//        val suggestedHeroesList = (suggestedHeroesResult as NetworkResponse.Success).body as List<HeroModel>
//        val heroesListsWithSeparationModels = createHeroListWithSeparation(suggestedHeroesList)
//        heroesResult.body.heroesList.forEach { hero ->
//            val id = hero.id
//            val imageUrl = hero.image.url
//            val heroName = hero.name
//            heroesListsWithSeparationModels.add(HeroModel(id, heroName, imageUrl))
//        }
//        return NetworkResponse.Success(heroesListsWithSeparationModels, code = SUCCESS_RESULT_CODE)
    }

    /**
     * @param addSeparation - We could use this function as the sole filler for the list or to get the
     * suggested heroes list combined with the search query results. If true, this means we are only showing the
     * recommended heroes and should add separation ViewHolders.
     */
    override suspend fun getSuggestedHeroesList(): NetworkResponse<List<BaseHeroModel>, String> {
        //TODO - continue from here, removing the internal call to this function and the boolean
        val suggestedHeroesList = mutableListOf<Deferred<NetworkResponse<HeroModel, String>>>()
        coroutineScope {
            DefaultData.SUGGESTED_HEROES_LIST.forEach { heroName ->
                val hero = async { getHero(heroName) }
                suggestedHeroesList.add(hero)
            }
        }
        val suggestedHeroes = mutableListOf<HeroModel>()
        suggestedHeroesList.awaitAll().forEach { networkResponse ->
            if (networkResponse is NetworkResponse.Error) return@forEach
            val hero = (networkResponse as NetworkResponse.Success).body
            suggestedHeroes.add(hero)
        }
        return NetworkResponse.Success(suggestedHeroes, code = SUCCESS_RESULT_CODE)
    }

    private suspend fun getHeroesByName(name: String) = heroesApi.getHeroesByName(NetworkConstants.TOKEN, name)

    private suspend fun getHero(name: String): NetworkResponse<HeroModel, String> {
        val heroesResult = getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return NetworkResponse.ServerError(body = null, code = 400)
        val responseNotSuccessful = (heroesResult as NetworkResponse.Success).body.response != SUCCESS_RESULT_RESPONSE
        if (responseNotSuccessful || heroesResult.body.heroesList.isEmpty()) {
            return NetworkResponse.UnknownError(Throwable(App.instance.getString(R.string.remote_hero_data_source_hero_not_found)))
        }
        //We assume that only one hero exists with the exact same name so we take the first in the list
        val id = heroesResult.body.heroesList[0].id
        val imageUrl = heroesResult.body.heroesList[0].image.url
        val heroName = heroesResult.body.heroesList[0].name
        return NetworkResponse.Success(HeroModel(id, heroName, imageUrl), code = SUCCESS_RESULT_CODE)
    }


}