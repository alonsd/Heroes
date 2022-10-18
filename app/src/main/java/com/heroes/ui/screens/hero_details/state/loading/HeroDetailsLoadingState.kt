package com.heroes.ui.screens.hero_details.state.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heroes.core.extensions.rangeToIntList
import com.heroes.core.ui.shimmering.heroesShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun HeroDetailsLoadingState() {
    Box {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .shimmer(heroesShimmer()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(200.dp)
                    .width(150.dp)
                    .background(Color.Gray)
            )
            Box(
                Modifier
                    .padding(18.dp)
                    .width(100.dp)
                    .height(25.dp)
                    .background(Color.Gray),
            )
            Box(
                Modifier
                    .padding(32.dp)
                    .width(170.dp)
                    .height(25.dp)
                    .background(Color.Gray),
            )

            LazyRow {
                items(rangeToIntList(0..3)) {
                    HeroDetailsCardListShimmerItem()
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            content = {},
            onClick = {},
            backgroundColor = Color.Gray)
    }

}

@Preview
@Composable
fun HeroDetailsLoadingStatePreview() {
    HeroDetailsLoadingState()
}