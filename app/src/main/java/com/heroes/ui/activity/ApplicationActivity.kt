package com.heroes.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import com.heroes.ui.application_flow.dashboard.screen.DashboardScreen

@ExperimentalComposeUiApi
class ApplicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashboardScreen()
        }
    }
}