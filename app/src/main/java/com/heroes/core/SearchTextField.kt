package com.heroes.core

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        searchState = SearchState(
            "Alon",
            focused = false,
            searching = true
        ),
        onQueryChanged = { },
        onSearchFocusChanged = { },
        onClearQueryClicked = { },
    )
}

@Composable
fun SearchTextField(
    searchState: SearchState,
    onQueryChanged: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
    onClearQueryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focused = searchState.focused
    var query = searchState.query
    val searching = searchState.searching

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
                modifier = modifier
            ) {

                if (query.isEmpty()) {
                    SearchHint(modifier = modifier.padding(start = 24.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = query,
                        onValueChange = {
                            query = it
                            onQueryChanged(it)
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


class SearchState(
    query: String,
    focused: Boolean,
    searching: Boolean,
) {

    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)

    override fun toString(): String {
        return "State query: $query, focused: $focused, searching: $searching "
    }
}