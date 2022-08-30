package com.heroes.core.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.heroes.core.constants.SharedPreferencesConstants.HEROES_SHARED_PREFERENCES


val Context.sharedPreferences: SharedPreferences
    get() = getSharedPreferences(HEROES_SHARED_PREFERENCES, MODE_PRIVATE)

val Activity.sharedPreferences: SharedPreferences
    get() = getSharedPreferences(HEROES_SHARED_PREFERENCES, MODE_PRIVATE)

val Application.sharedPreferences: SharedPreferences
    get() = getSharedPreferences(HEROES_SHARED_PREFERENCES, MODE_PRIVATE)

private fun SharedPreferences.editSharedPreferences(operation: (SharedPreferences.Editor) -> Unit) {
    val editSharePreferences = edit()
    operation(editSharePreferences)
    editSharePreferences.apply()
}

private fun SharedPreferences.Editor.put(pair: Pair<String, Any?>) {
    val key = pair.first
    when (val value = pair.second) {
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
    }
}
