package com.heroes.di

import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.data.repository.HeroesDetailsRepositoryImpl
import com.heroes.data.repository.HeroesRepository
import com.heroes.data.repository.HeroesRepositoryImpl
import com.heroes.data.source.remote.source.hero.RemoteHeroDataSource
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideHeroesRepository(remoteHeroDataSource: RemoteHeroDataSource) : HeroesRepository{
        return HeroesRepositoryImpl(remoteHeroDataSource)
    }

    @Provides
    @Singleton
    fun provideHeroesDetailsRepository(remoteHeroDetailsDataSource: RemoteHeroDetailsDataSource) : HeroesDetailsRepository{
        return HeroesDetailsRepositoryImpl(remoteHeroDetailsDataSource)
    }
}