package com.heroes.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        modifier = Modifier,
        searchState = SearchState(
            "Ethan Hunt",
            focused = true,
            searching = false
        ),
        {},
        {},
        {},
        {},
        FocusRequester()
    )
}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onQueryChanged: (String) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQueryClicked: () -> Unit,
    onBack: () -> Unit,
    focusRequester : FocusRequester,
    keyboardController: SoftwareKeyboardController? = null
) {
    val focusManager = LocalFocusManager.current
    val focused = searchState.focused

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            BackButton(focusManager, keyboardController, onBack)
        }

        SearchTextField(
            searchState = searchState,
            onQueryChanged = onQueryChanged,
            onSearchFocusChanged = onSearchFocusChange,
            onClearQueryClicked = onClearQueryClicked,
            focusRequester = focusRequester,
            focusManager = focusManager,
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

