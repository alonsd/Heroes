package com.heroes.data.repository

import com.heroes.data.source.remote.source.hero.RemoteHeroDataSourceImp

class HeroesRepositoryImpl(private val remoteHeroDataSource: RemoteHeroDataSourceImp) {
    
    suspend fun getHeroesByNameWithSuggestions(name: String) =
        remoteHeroDataSource.getHeroesByNameWithSuggestions(name)

    suspend fun getSuggestedHeroesList(addSeparation: Boolean) =
        remoteHeroDataSource.getSuggestedHeroesList(addSeparation)

}

