package com.heroes.ui.application_flow.dashboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.heroes.core.ui.search.SearchBar
import com.heroes.model.ui_models.heroes_list.BaseHeroModel
import com.heroes.model.ui_models.heroes_list.HeroModel
import com.heroes.model.ui_models.heroes_list.HeroSeparatorModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem
import com.heroes.ui.application_flow.dashboard.viewmodel.DashboardViewModel

@ExperimentalComposeUiApi
@Composable
fun DashboardDataState(
    viewModel: DashboardViewModel,
    heroesList: List<BaseHeroModel>
) {
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val searchState by viewModel.searchState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        SearchBar(
            searchState = searchState,
            onSearchQueryChanged = { text ->
                viewModel.submitEvent(DashboardViewModel.UiEvent.SearchQueryChanged(text))
            },
            onSearchFocusChange = { focused ->
                viewModel.submitEvent(DashboardViewModel.UiEvent.SearchBarFocusChanged(focused))
            },
            onClearQueryClicked = {
                viewModel.submitEvent(DashboardViewModel.UiEvent.ClearQueryClicked)
            },
            onBack = {},
            focusRequester = focusRequester,
            keyboardController = keyboardController
        )
        LazyColumn(state = listState) {
            items(heroesList) { model ->
                viewModel.submitEvent(DashboardViewModel.UiEvent.ListIsScrolling(listState.isScrollInProgress))
                if (model is HeroSeparatorModel)
                    HeroesListSeparatorItem(model)
                else if (model is HeroModel)
                    HeroesListItem(model) {
                        viewModel.submitEvent(DashboardViewModel.UiEvent.ListItemClicked(model))
                    }
            }
        }
    }
}