package com.heroes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.heroes.core.extensions.setHeroesTheme
import com.heroes.ui.application_flow.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class ApplicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeroesTheme {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }
    }
}