package com.heroes.core.ui.shimmering

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun DefaultHeroesShimmering(): Shimmer {
    return rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = ShimmerTheme(
            animationSpec = infiniteRepeatable(
                animation = tween(
                    600,
                    easing = LinearEasing,
                    delayMillis = 300,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            blendMode = BlendMode.DstIn,
            rotation = 15.0f,
            shaderColors = listOf(
                Color.Unspecified.copy(alpha = 0.25f),
                Color.Unspecified.copy(alpha = 1.00f),
                Color.Unspecified.copy(alpha = 0.25f),
            ),
            shaderColorStops = listOf(
                0.0f,
                0.5f,
                1.0f,
            ),
            shimmerWidth = 400.dp,
        )
    )
}