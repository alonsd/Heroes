package com.heroes.service_locator

import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel
import com.heroes.data.viewmodel.HeroesListItemViewModel
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HeroesViewModel(get()) }
    viewModel { HeroesListItemViewModel() }
    viewModel { HeroesDetailsViewModel(get()) }
}