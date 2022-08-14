package com.heroes.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        searchState = SearchState(
            "Ethan Hunt",
            focused = true,
            searching = false
        ),
        {},
        {},
        {},
        {},
        FocusRequester(),
        modifier = Modifier
    )
}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    searchState: SearchState,
    onQueryChanged: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQueryClicked: () -> Unit,
    onBack: () -> Unit,
    focusRequester : FocusRequester,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focused = searchState.focused

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            BackButton(focusManager, keyboardController, onBack)
        }

        SearchTextField(
            searchState,
            onQueryChanged,
            onSearchFocusChange,
            onClearQueryClicked,
            focusRequester
        )
    }
}

@ExperimentalComposeUiApi
@Composable
private fun BackButton(
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    onBack: () -> Unit
) {
    IconButton(
        modifier = Modifier.padding(start = 2.dp),
        onClick = {
            focusManager.clearFocus()
            keyboardController?.hide()
            onBack()
        }) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
    }
}

