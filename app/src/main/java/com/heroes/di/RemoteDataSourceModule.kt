package com.heroes.di

import android.app.Application
import android.content.Context
import com.heroes.data.source.remote.api.HeroesApi
import com.heroes.data.source.remote.api.HeroesDetailsApi
import com.heroes.data.source.remote.source.hero.RemoteHeroDataSource
import com.heroes.data.source.remote.source.hero.RemoteHeroDataSourceImp
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSource
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
   fun provideRemoteHeroDataSource(heroesApi: HeroesApi, app: Application) : RemoteHeroDataSource {
       return RemoteHeroDataSourceImp(heroesApi, app)
   }

    @Provides
    @Singleton
   fun provideRemoteHeroDetailsDataSource(heroesDetailsApi: HeroesDetailsApi) : RemoteHeroDetailsDataSource {
       return RemoteHeroDetailsDataSourceImp(heroesDetailsApi)
   }
}