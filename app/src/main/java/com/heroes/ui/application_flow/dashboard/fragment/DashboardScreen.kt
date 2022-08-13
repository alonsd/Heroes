package com.heroes.ui.application_flow.dashboard.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.heroes.core.SearchBar
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import org.koin.androidx.compose.get

@ExperimentalComposeUiApi
@Composable
fun DashboardScreen(heroesViewModel: HeroesViewModel = get(), navController: NavController? = null) {
    val searchState by heroesViewModel.searchState.collectAsState()
    val uiState by heroesViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        SearchBar(
            searchState = searchState,
            onQueryChanged = { text ->
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.SearchQueryChanged(text))
            },
            onSearchFocusChange = { focused ->
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.SearchFocusChanged(focused))
            },
            onClearQueryClicked = {
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.ClearQueryClicked)
            },
            onBack = {
//                navController.popBackStack()
            }
        )

        LazyColumn {
            items(uiState.modelsListResponse ?: listOf()) { model ->
                if (model is HeroListSeparatorModel)
                    HeroesListSeparatorItem(model)
                else if (model is HeroesListModel)
                    HeroesListItem(model) {
                        heroesViewModel.submitEvent(HeroesViewModel.UiEvent.ListItemClicked(model))
                    }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}