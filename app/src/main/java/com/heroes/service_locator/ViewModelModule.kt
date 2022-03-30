package com.heroes.service_locator

import com.heroes.data.viewmodel.HeroesDetailsViewModel
import com.heroes.data.viewmodel.HeroesListItemViewModel
import com.heroes.data.viewmodel.HeroesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HeroesViewModel(get()) }
    viewModel { HeroesListItemViewModel() }
    viewModel { HeroesDetailsViewModel(get()) }
}