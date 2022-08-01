

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        TextFieldValue("גגגגגגג"),
        {},
        { },
        {},
        {},
        searching = false,
        focused = true,
        modifier = Modifier
    )
}


@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            backButton(focusManager, keyboardController, onBack)
        }
    }

    SearchTextField(
        query,
        onQueryChanged,
        onSearchFocusChange,
        onClearQuery,
        searching,
        focused,
        modifier
    )
}

@ExperimentalComposeUiApi
@Composable
private fun backButton(focusManager: FocusManager, keyboardController: SoftwareKeyboardController?, onBack: () -> Unit) {
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

