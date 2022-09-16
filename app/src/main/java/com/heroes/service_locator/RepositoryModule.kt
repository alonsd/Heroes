package com.heroes.service_locator

import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.data.repository.HeroesDetailsRepositoryImpl
import com.heroes.data.repository.HeroesRepository
import com.heroes.data.repository.HeroesRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val repositoryModule = module {

    singleOf(::HeroesRepositoryImpl) { bind<HeroesRepository>() }
    singleOf(::HeroesDetailsRepositoryImpl) { bind<HeroesDetailsRepository>() }
}