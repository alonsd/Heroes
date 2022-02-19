package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.source.remote.source.RemoteHeroDataSource
import com.bankhapoalimheroes.data.source.remote.source.RemoteHeroDetailsDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { RemoteHeroDataSource(get()) }
    single { RemoteHeroDetailsDataSource(get()) }
}