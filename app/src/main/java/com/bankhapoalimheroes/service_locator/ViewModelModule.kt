package com.bankhapoalimheroes.service_locator

import com.bankhapoalimheroes.data.viewmodel.HeroesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HeroesViewModel(get()) }
}