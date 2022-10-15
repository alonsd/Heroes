package com.heroes.ui.application_flow.hero_details.state.loading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HeroDetailsCardListShimmerItem() {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(150.dp)
            .padding(15.dp),
        backgroundColor = Color.Gray,
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
    ) {}
}

@Preview
@Composable
fun HeroDetailsCardListShimmerItemPreview() {
    HeroDetailsCardListShimmerItem()
}