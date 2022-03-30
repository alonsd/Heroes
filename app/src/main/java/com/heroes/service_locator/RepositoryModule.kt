package com.heroes.service_locator

import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.data.repository.HeroesRepository
import org.koin.dsl.module


val repositoryModule = module {

    single { HeroesRepository(get()) }
    single { HeroesDetailsRepository(get()) }
}