package com.heroes.di

import com.heroes.data.source.remote.source.hero.RemoteHeroDataSource
import com.heroes.data.source.remote.source.hero.RemoteHeroDataSourceImpl
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSource
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteHeroDataSource(
        remoteHeroDataSourceImpl: RemoteHeroDataSourceImpl
    ): RemoteHeroDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteHeroDetailsDataSource(
        remoteHeroDetailsDataSourceImpl: RemoteHeroDetailsDataSourceImpl
    ): RemoteHeroDetailsDataSource


}