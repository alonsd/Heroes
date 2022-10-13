package com.heroes.ui.application_flow.dashboard.screen

import android.content.res.Configuration
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
import com.heroes.core.ui.search.SearchBar
import com.heroes.core.ui.search.SearchState
import com.heroes.model.ui_models.heroes_list.BaseHeroModel
import com.heroes.model.ui_models.heroes_list.HeroModel
import com.heroes.model.ui_models.heroes_list.HeroSeparatorModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem

@ExperimentalComposeUiApi
@Composable
fun DashboardDataState(
    heroesList: List<BaseHeroModel>,
    searchState: SearchState,
    onSearchQueryChanged: (text: String) -> Unit,
    onSearchFocusChange: (focused: Boolean) -> Unit,
    onClearQueryClicked: () -> Unit,
    onListScrolling: (isScrolling: Boolean) -> Unit,
    onListItemClicked: (model: HeroModel) -> Unit
) {
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Column(modifier = Modifier.fillMaxSize()) {

        SearchBar(
            searchState = searchState,
            onSearchQueryChanged = { text -> onSearchQueryChanged(text) },
            onSearchFocusChange = { focused -> onSearchFocusChange(focused) },
            onClearQueryClicked = { onClearQueryClicked() },
            onBack = {},
            focusRequester = focusRequester,
            keyboardController = keyboardController
        )
        LazyColumn(state = listState) {
            items(heroesList) { model ->
                onListScrolling(listState.isScrollInProgress)
                if (model is HeroSeparatorModel)
                    HeroesListSeparatorItem(model)
                else if (model is HeroModel)
                    HeroesListItem(model) {
                        onListItemClicked(model)
                    }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(showBackground = true ,uiMode = Configuration.UI_MODE_NIGHT_NO)
fun DashboardDataStatePreview() {
    DashboardDataState(listOf(
        HeroModel("", "Alon", ""),
        HeroModel("", "Alon2", ""),
        HeroModel("", "Alon3", ""),
    ), SearchState("", focused = false, searching = false), {}, {}, {}, {}, {})
}