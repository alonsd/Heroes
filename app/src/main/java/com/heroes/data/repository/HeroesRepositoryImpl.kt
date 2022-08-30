package com.heroes.data.repository

import com.heroes.data.source.remote.source.hero.RemoteHeroDataSourceImp

class HeroesRepositoryImpl(private val remoteHeroDataSource: RemoteHeroDataSourceImp) : HeroesRepository {
    
    override suspend fun getHeroesByNameWithSuggestions(name: String) =
        remoteHeroDataSource.getHeroesByNameWithSuggestions(name)

    override suspend fun getSuggestedHeroesList(addSeparation: Boolean) =
        remoteHeroDataSource.getSuggestedHeroesList(addSeparation)

}

