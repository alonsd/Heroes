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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        query = TextFieldValue("Alon"),
        onQueryChanged = { },
        onSearchFocusChanged = { },
        onClearQuery = { },
        searching = true,
        focused = false
    )
}

@Composable
fun SearchTextField(
    query: TextFieldValue,
    onQueryChanged: (TextFieldValue) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

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

                if (query.text.isEmpty()) {
                    SearchHint(modifier = modifier.padding(start = 24.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChanged,
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
                                    .padding(horizontal = 6.dp)
                                    .size(36.dp)
                            )
                        }

                        query.text.isNotEmpty() -> {
                            IconButton(onClick = onClearQuery) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = null)
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
    hint: String = "Enter a hero name",
    modifier: Modifier = Modifier
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

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
    searchResults: List<String> = emptyList()
) = remember { SearchState(query, focused, searching, searchResults) }


class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    searchResults: List<String>
) {

    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var searchResults by mutableStateOf(searchResults)

    val searchDisplay: SearchDisplay
        get() = when {
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }

    override fun toString(): String {
        return "🚀 State query: $query, focused: $focused, searching: $searching " +
                "searchResults: ${searchResults.size}, " +
                " searchDisplay: $searchDisplay"
    }
}

enum class SearchDisplay {
    Results,
    NoResults
}