package com.heroes.ui.screens.dashboard.state.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.heroes.core.extensions.rangeToIntList
import com.heroes.core.ui.search.SearchBarShimmerTextField
import com.heroes.core.ui.shimmering.heroesShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun DashboardScreenLoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .shimmer(heroesShimmer())
    ) {
        LazyColumn {
            itemsIndexed(rangeToIntList(0..20)) { index, _ ->
                when (index) {
                    0 -> SearchBarShimmerTextField()
                    1, 5 -> ListSeparatorShimmer()
                    else -> ListItemShimmer()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DashboardScreenLoadingStatePreview() {
    DashboardScreenLoadingState()
}