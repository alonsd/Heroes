package com.heroes.ui.application_flow.dashboard.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.heroes.core.ui.loading.GeneralLoadingState
import com.heroes.ui.application_flow.dashboard.viewmodel.DashboardViewModel
import com.heroes.ui.application_flow.destinations.HeroDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get

@RootNavGraph(start = true)
@ExperimentalComposeUiApi
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    viewModel: DashboardViewModel = get()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = "") {
        viewModel.uiAction.collect { uiAction ->
            when (uiAction) {
                is DashboardViewModel.UiAction.NavigateToHeroesDetails -> {
                    navigator.navigate(HeroDetailsScreenDestination(uiAction.heroModel))
                }
            }
        }
    }


    when (uiState.state) {
        DashboardViewModel.UiState.State.Data -> {
            DashboardDataState(viewModel, uiState.heroesList)
        }
        DashboardViewModel.UiState.State.Error -> {

        }
        DashboardViewModel.UiState.State.Initial -> {
            DashboardScreenLoadingState()
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun DashboardScreenPreview() {
//    DashboardScreen()
}