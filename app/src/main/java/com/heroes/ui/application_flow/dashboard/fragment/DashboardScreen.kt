package com.heroes.ui.application_flow.dashboard.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.heroes.core.SearchBar
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.java.KoinJavaComponent.get

@ExperimentalComposeUiApi
@Composable
fun DashboardScreen(heroesViewModel: HeroesViewModel = get()) {
    val searchState by heroesViewModel.searchState.collectAsState()
    val uiState by heroesViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        SearchBar(
            searchState = searchState,
            onQueryChanged = { text ->
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.SearchQueryChanged(text))
            },
            onSearchFocusChange = { },
            onClearQueryClicked = {
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.ClearQueryClicked)
            },
            onBack = { }
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