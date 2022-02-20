package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.viewmodel.HeroesDetailsViewModel
import com.bankhapoalimheroes.data.viewmodel.HeroesListItemViewModel
import com.bankhapoalimheroes.data.viewmodel.HeroesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HeroesViewModel(get()) }
    viewModel { HeroesListItemViewModel() }
    viewModel { HeroesDetailsViewModel(get()) }
}