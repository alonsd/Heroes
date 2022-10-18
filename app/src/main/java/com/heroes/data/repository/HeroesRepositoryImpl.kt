package com.heroes.data.repository

import com.heroes.data.source.remote.source.hero.RemoteHeroDataSource
import javax.inject.Inject

class HeroesRepositoryImpl @Inject constructor(
    private val remoteHeroDataSource: RemoteHeroDataSource
) : HeroesRepository {

    override suspend fun getHeroesByName(name: String) =
        remoteHeroDataSource.getHeroesByName(name)

    override suspend fun getSuggestedHeroesList() = remoteHeroDataSource.getSuggestedHeroesList()

}

