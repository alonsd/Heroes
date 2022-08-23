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
    viewModel: HeroesViewModel = get()
) {

    val searchState by viewModel.searchState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }


    LaunchedEffect(key1 = viewModel.uiAction) {
        viewModel.uiAction.collect { uiAction ->
            when (uiAction) {
                is HeroesViewModel.UiAction.NavigateToHeroesDetails -> {
                    navigator.navigate(HeroDetailsScreenDestination(uiAction.heroModel))
                }
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {

        SearchBar(
            searchState = searchState,
            onSearchQueryChanged = { text ->
                viewModel.submitEvent(HeroesViewModel.UiEvent.SearchQueryChanged(text))
            },
            onSearchFocusChange = { focused ->
                viewModel.submitEvent(HeroesViewModel.UiEvent.SearchBarFocusChanged(focused))
            },
            onClearQueryClicked = {
                viewModel.submitEvent(HeroesViewModel.UiEvent.ClearQueryClicked)
            },
            onBack = {},
            focusRequester = focusRequester,
            keyboardController = keyboardController
        )
        LazyColumn(state = listState) {
            items(uiState.modelsListResponse ?: listOf()) { model ->
                viewModel.submitEvent(HeroesViewModel.UiEvent.ListIsScrolling(listState.isScrollInProgress))
                if (model is HeroListSeparatorModel)
                    HeroesListSeparatorItem(model)
                else if (model is HeroesListModel)
                    HeroesListItem(model) {
                        viewModel.submitEvent(HeroesViewModel.UiEvent.ListItemClicked(model))
                    }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun DashboardScreenPreview() {
//    DashboardScreen()
}