package com.bankhapoalimheroes.data.source.remote.source

import com.bankhapoalimheroes.data.source.remote.api.HeroesApi
import com.bankhapoalimheroes.utils.constants.NetworkConstants

class RemoteHeroDataSource(private val heroesApi: HeroesApi) {

    /**
     * In a real application the token would not be saved hard-coded but saved safely in local database.
     */
    suspend fun getHeroesByName(name : String) = heroesApi.getHeroesByName(NetworkConstants.TOKEN, name)

}