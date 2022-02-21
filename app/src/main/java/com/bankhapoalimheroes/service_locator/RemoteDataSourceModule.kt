package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.source.remote.source.hero.RemoteHeroDataSourceImp
import com.bankhapoalimheroes.data.source.remote.source.hero_details.RemoteHeroDetailsDataSourceImp
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { RemoteHeroDataSourceImp(get()) }
    single { RemoteHeroDetailsDataSourceImp(get()) }
}