package com.heroes.core.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        searchState = SearchState(
            "Ethan Hunt",
            focused = false,
            searching = true
        ),
        onSearchQueryChanged = { },
        onSearchFocusChanged = { },
        onClearQueryClicked = { },
        focusRequester = FocusRequester(),
    )
}

@Preview
@Composable
fun SearchBarShimmerPreview() {
    SearchBarShimmerTextField()
}

@Composable
fun SearchBarShimmerTextField() {
    SearchTextField(
        shimmeringModifier = Modifier.background(Color.Gray),
        searchState = SearchState(
            "",
            focused = false,
            searching = false
        ),
        onSearchQueryChanged = { },
        onSearchFocusChanged = { },
        onClearQueryClicked = { },
        focusRequester = FocusRequester(),
    )
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    shimmeringModifier: Modifier? = null,
    searchState: SearchState,
    onSearchQueryChanged: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
    onClearQueryClicked: () -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager = LocalFocusManager.current
) {
    val focused = searchState.focused
    var query = searchState.query
    val searching = searchState.searching

    if (focused.not()) {
        focusManager.clearFocus()
    }

    Surface(
        modifier = modifier
            .then(
                Modifier
                    .height(56.dp)
                    .padding(
                        top = 8.dp, bottom = 8.dp,
                        start = if (focused.not()) 16.dp else 0.dp,
                        end = 16.dp
                    )
            ),
        color = Color(0xffF5F5F5),
        shape = RoundedCornerShape(percent = 50)
    ) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = shimmeringModifier ?: modifier
            ) {

                if (query.isEmpty()) {
                    SearchHint(modifier = modifier.padding(start = 24.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = query,
                        onValueChange = {
                            query = it
                            onSearchQueryChanged(it)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .onFocusChanged { focusState ->
                                onSearchFocusChanged(focusState.isFocused)
                            }
                            .focusRequester(focusRequester)
                            .padding(top = 9.dp, bottom = 8.dp, start = 24.dp, end = 8.dp),
                        singleLine = true
                    )
                    when {
                        searching -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .width(25.dp)
                                    .size(24.dp)
                            )
                        }

                        query.isNotEmpty() -> {
                            IconButton(onClick = onClearQueryClicked) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchHint(
    modifier: Modifier = Modifier,
    hint: String = "Enter a hero name"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)

    ) {
        Text(
            text = hint,
            color = Color(0xff757575)
        )
    }
}


data class SearchState(
    val query: String,
    val focused: Boolean,
    val searching: Boolean,
)