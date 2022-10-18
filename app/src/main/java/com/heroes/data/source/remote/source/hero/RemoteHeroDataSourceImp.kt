package com.heroes.data.source.remote.source.hero

import android.content.Context
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.R
import com.heroes.core.constants.DefaultData
import com.heroes.core.constants.NetworkConstants
import com.heroes.core.constants.NetworkConstants.SUCCESS_RESULT_CODE
import com.heroes.core.constants.NetworkConstants.SUCCESS_RESULT_RESPONSE
import com.heroes.data.source.remote.api.HeroesApi
import com.heroes.model.ui_models.heroes_list.HeroModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RemoteHeroDataSourceImp @Inject constructor(
    private val heroesApi: HeroesApi,
    private val context: Context
) : RemoteHeroDataSource {


    override suspend fun getHeroesByName(name: String): NetworkResponse<List<HeroModel>, String> {
        val heroesResult = getAllHeroesContainingName(name)
        if (heroesResult is NetworkResponse.Error)
            return NetworkResponse.ServerError(
                code = 400,
                body = heroesResult.error.localizedMessage ?: ""
            )
        if ((heroesResult as NetworkResponse.Success).body.response != SUCCESS_RESULT_RESPONSE) {
            return NetworkResponse.Success(emptyList(), code = SUCCESS_RESULT_CODE)
        }
        val heroesList = mutableListOf<HeroModel>()
        heroesResult.body.heroesList.forEach { hero ->
            heroesList.add(HeroModel(hero.id, hero.name, hero.image.url))
        }
        return NetworkResponse.Success(heroesList, code = SUCCESS_RESULT_CODE)
    }

    override suspend fun getSuggestedHeroesList(): NetworkResponse<List<HeroModel>, String> {
        val suggestedHeroesList = mutableListOf<Deferred<NetworkResponse<HeroModel, String>>>()
        coroutineScope {
            DefaultData.SUGGESTED_HEROES_LIST.forEach { heroName ->
                val hero = async { getFirstHeroContainingName(heroName) }
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

    private suspend fun getAllHeroesContainingName(name: String) = heroesApi.getHeroesByName(NetworkConstants.TOKEN, name)

    private suspend fun getFirstHeroContainingName(name: String): NetworkResponse<HeroModel, String> {
        val heroesResult = getAllHeroesContainingName(name)
        if (heroesResult is NetworkResponse.Error) return NetworkResponse.ServerError(body = null, code = 400)
        val responseNotSuccessful = (heroesResult as NetworkResponse.Success).body.response != SUCCESS_RESULT_RESPONSE
        if (responseNotSuccessful || heroesResult.body.heroesList.isEmpty()) {
            return NetworkResponse.UnknownError(Throwable(context.getString(R.string.remote_hero_data_source_hero_not_found)))
        }
        val id = heroesResult.body.heroesList[0].id
        val imageUrl = heroesResult.body.heroesList[0].image.url
        val heroName = heroesResult.body.heroesList[0].name
        return NetworkResponse.Success(HeroModel(id, heroName, imageUrl), code = SUCCESS_RESULT_CODE)
    }


}