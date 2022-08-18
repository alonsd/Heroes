package com.heroes.ui.application_flow.dashboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.heroes.core.SearchBar
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import org.koin.androidx.compose.get

@ExperimentalComposeUiApi
@Composable
fun DashboardScreen(heroesViewModel: HeroesViewModel = get()) {

    val searchState by heroesViewModel.searchState.collectAsState()
    val uiState by heroesViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }


    Column(modifier = Modifier.fillMaxSize()) {

        SearchBar(
            searchState = searchState,
            onSearchQueryChanged = { text ->
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.SearchQueryChanged(text))
            },
            onSearchFocusChange = { focused ->
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.SearchBarFocusChanged(focused))
            },
            onClearQueryClicked = {
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.ClearQueryClicked)
            },
            onBack = {},
            focusRequester = focusRequester,
            keyboardController = keyboardController
        )
        LazyColumn(state = listState) {
            items(uiState.modelsListResponse ?: listOf()) { model ->
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.ListIsScrolling(listState.isScrollInProgress))
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