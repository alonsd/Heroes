package com.heroes.service_locator

import com.heroes.data.viewmodel.HeroesListItemViewModel
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::HeroesViewModel)
    viewModelOf(::HeroesListItemViewModel)
//    viewModel { params ->
//        HeroesDetailsViewModel(params.get(), get())
//    }
    viewModel {
        HeroesDetailsViewModel(get())
    }
}