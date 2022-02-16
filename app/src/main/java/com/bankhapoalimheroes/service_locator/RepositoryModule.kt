package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.repository.HeroesRepository
import org.koin.dsl.module


val repositoryModule = module {

    single { HeroesRepository(get(), get()) }
}