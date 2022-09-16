package com.heroes.data.repository

import com.heroes.data.source.remote.source.hero.RemoteHeroDataSourceImp

class HeroesRepositoryImpl(private val remoteHeroDataSource: RemoteHeroDataSourceImp) : HeroesRepository {
    
    override suspend fun getHeroesByName(name: String) =
        remoteHeroDataSource.getHeroesByName(name)

    override suspend fun getSuggestedHeroesList() = remoteHeroDataSource.getSuggestedHeroesList()

}

