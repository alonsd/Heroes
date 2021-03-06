package com.heroes.data.source.remote.source.hero

import com.heroes.R
import com.heroes.data.source.remote.api.HeroesApi
import com.heroes.model.ui_models.heroes_list.BaseHeroListModel
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType
import com.heroes.utils.application.App
import com.heroes.utils.constants.DefaultData
import com.heroes.utils.constants.NetworkConstants
import com.heroes.utils.constants.NetworkConstants.SUCCESS_RESULT_RESPONSE
import com.haroldadmin.cnradapter.NetworkResponse
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
            return NetworkResponse.UnknownError(Throwable(App.instance.getString(R.string.remote_hero_data_source)))
        }
        val suggestedHeroesList = (suggestedHeroesResult as NetworkResponse.Success).body as List<HeroesListModel>
        val heroesListsWithSeparationModels = createHeroListWithSeparation(suggestedHeroesList)
        heroesResult.body.heroesList.forEach { hero ->
            val id = hero.id
            val imageUrl = hero.image.url
            val heroName = hero.name
            heroesListsWithSeparationModels.add(HeroesListModel(id, heroName, imageUrl))
        }
        return NetworkResponse.Success(heroesListsWithSeparationModels, code = NetworkConstants.SUCCESS_RESULT_CODE)
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
        val suggestedHeroes = mutableListOf<HeroesListModel>()
        val awaitAll = suggestedHeroesList.awaitAll()
        awaitAll.forEach { networkResponse ->
            if (networkResponse is NetworkResponse.Error) return networkResponse
            val element = (networkResponse as NetworkResponse.Success).body as List<HeroesListModel>
            /*Response could be more then 1 hero when fetching data but we assume that
            when given an exact name we will get only one result and it will be correct.*/
            suggestedHeroes.add(element[0])
        }
        if (addSeparation.not())
            return NetworkResponse.Success(suggestedHeroes, code = NetworkConstants.SUCCESS_RESULT_CODE)
        val heroesListWithSeparation = createHeroListWithSeparation(suggestedHeroes)
        return NetworkResponse.Success(heroesListWithSeparation, code = NetworkConstants.SUCCESS_RESULT_CODE)
    }

    private suspend fun getHeroesByName(name : String) = heroesApi.getHeroesByName(NetworkConstants.TOKEN, name)

    private fun createHeroListWithSeparation(suggestedHeroesResult: List<HeroesListModel>): MutableList<BaseHeroListModel> {
        val heroesListsModels = mutableListOf<BaseHeroListModel>()
        heroesListsModels.add(0, HeroListSeparatorModel(HeroesListSeparatorType.SUGGESTIONS))
        heroesListsModels.addAll(suggestedHeroesResult)
        heroesListsModels.add(HeroListSeparatorModel(HeroesListSeparatorType.SEARCH))
        return heroesListsModels
    }

    private suspend fun getHeroesListContainingName(name: String): NetworkResponse<*, String> {
        val heroesResult = getHeroesByName(name)
        if (heroesResult is NetworkResponse.Error) return heroesResult
        if ((heroesResult as NetworkResponse.Success).body.response != SUCCESS_RESULT_RESPONSE) {
            return NetworkResponse.UnknownError(Throwable(App.instance.getString(R.string.remote_hero_data_source)))
        }
        val heroesListsModels = mutableListOf<HeroesListModel>()
        heroesResult.body.heroesList.forEach { hero ->
            val id = hero.id
            val imageUrl = hero.image.url
            val heroName = hero.name
            heroesListsModels.add(HeroesListModel(id, heroName, imageUrl))
        }
        return NetworkResponse.Success(heroesListsModels, code = NetworkConstants.SUCCESS_RESULT_CODE)
    }


}