package com.heroes.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heroes.R

class ApplicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)
    }
}