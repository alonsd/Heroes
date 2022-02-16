package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.source.remote.source.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { RemoteDataSource(get()) }
}