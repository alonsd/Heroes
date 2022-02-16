package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.source.local.source.LocalDataSource
import com.bankhapoalimheroes.database.BasicApplicationDatabase
import org.koin.dsl.module

val localDataSourceModule = module {

    single { LocalDataSource(BasicApplicationDatabase.getInstance().basicApplicationDao()) }

}