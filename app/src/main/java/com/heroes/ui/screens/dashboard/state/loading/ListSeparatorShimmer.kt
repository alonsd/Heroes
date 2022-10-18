package com.heroes.ui.screens.dashboard.state.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListSeparatorShimmer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(32.dp)
    ){}
}

@Composable
@Preview(showBackground = true)
fun ListSeparatorShimmerPreview() {
    ListSeparatorShimmer()
}