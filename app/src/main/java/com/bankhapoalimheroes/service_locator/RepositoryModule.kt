package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.repository.HeroesDetailsRepository
import com.bankhapoalimheroes.data.repository.HeroesRepository
import org.koin.dsl.module


val repositoryModule = module {

    single { HeroesRepository(get(), get()) }
    single { HeroesDetailsRepository(get()) }
}