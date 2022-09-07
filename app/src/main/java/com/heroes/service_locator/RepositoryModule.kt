package com.heroes.service_locator

import com.heroes.data.repository.HeroesDetailsRepositoryImpl
import com.heroes.data.repository.HeroesRepositoryImpl
import com.heroes.ui.application_flow.dashboard.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {

    single { HeroesRepositoryImpl(get()) }
    single { HeroesDetailsRepositoryImpl(get()) }
    viewModel { DashboardViewModel(get ()) }
}