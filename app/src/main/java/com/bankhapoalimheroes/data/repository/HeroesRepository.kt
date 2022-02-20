package com.bankhapoalimheroes.data.repository

import com.bankhapoalimheroes.data.source.remote.source.RemoteHeroDataSource

class HeroesRepository(private val remoteHeroDataSource: RemoteHeroDataSource) {
    
    suspend fun getHeroesByNameWithSuggestions(name: String) =
        remoteHeroDataSource.getHeroesByNameWithSuggestions(name)

    suspend fun getSuggestedHeroesList(addSeparation: Boolean) =
        remoteHeroDataSource.getSuggestedHeroesList(addSeparation)

}

