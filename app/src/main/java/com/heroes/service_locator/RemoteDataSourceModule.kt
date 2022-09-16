package com.heroes.service_locator

import com.heroes.data.source.remote.source.hero.RemoteHeroDataSource
import com.heroes.data.source.remote.source.hero.RemoteHeroDataSourceImp
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSource
import com.heroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSourceImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val remoteDataSourceModule = module {
    singleOf(::RemoteHeroDetailsDataSourceImp) { bind<RemoteHeroDetailsDataSource>() }
    singleOf(::RemoteHeroDataSourceImp) { bind<RemoteHeroDataSource>() }
}