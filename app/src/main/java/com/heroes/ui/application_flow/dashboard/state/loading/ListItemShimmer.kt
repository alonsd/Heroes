package com.heroes.ui.application_flow.dashboard.state.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListItemShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
                .clip(CircleShape)
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .padding(start = 16.dp)
                .background(Color.Gray)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ListItemShimmerPreview() {
    ListItemShimmer()
}