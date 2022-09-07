package com.heroes.service_locator

import com.heroes.ui.application_flow.dashboard.viewmodel.DashboardViewModel
import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::DashboardViewModel)
    viewModel { params ->
        HeroesDetailsViewModel(params.get(), get())
    }
}