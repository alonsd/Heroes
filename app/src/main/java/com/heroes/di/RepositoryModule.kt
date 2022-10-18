package com.heroes.di

import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.data.repository.HeroesDetailsRepositoryImpl
import com.heroes.data.repository.HeroesRepository
import com.heroes.data.repository.HeroesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHeroesRepository(
        heroesRepositoryImpl: HeroesRepositoryImpl
    ): HeroesRepository

    @Binds
    @Singleton
    abstract fun bindHeroesDetailsRepository(
        heroesDetailsRepositoryImpl: HeroesDetailsRepositoryImpl
    ): HeroesDetailsRepository
}