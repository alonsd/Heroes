package com.heroes.service_locator

import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.data.repository.HeroesRepository
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {

    single { HeroesRepository(get()) }
    single { HeroesDetailsRepository(get()) }
    viewModel { HeroesViewModel(get ()) }
}