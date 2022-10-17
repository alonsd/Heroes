package com.heroes.core.extensions

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.heroes.ui.theme.HeroesTheme

fun ComponentActivity.setHeroesContent(content: @Composable () -> Unit){
    setContent {
        HeroesTheme {
            content()
        }
    }
}