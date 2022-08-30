package com.heroes.core.extensions

import android.content.Context
import android.content.Intent

fun shareInformationAsText(information: String, context: Context) =
    Intent().apply {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, information)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
