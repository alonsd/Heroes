package com.bankhapoalimheroes.utils.extensions

import android.app.Activity
import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.bankhapoalimheroes.utils.constants.SharedPreferencesConstants.BANK_HAPOALIM_HEROES_SHARED_PREFERENCES
import com.bankhapoalimheroes.utils.constants.SharedPreferencesConstants.IMAGES_CACHE_TIME


var SharedPreferences.imagesCacheTime
    get() = getLong(IMAGES_CACHE_TIME, 0)
    set(value) {
        editSharedPreferences { editor ->
            editor.put(IMAGES_CACHE_TIME to value)
        }
    }

val Fragment.sharedPreferences: SharedPreferences
    get() =
        requireActivity().getSharedPreferences(BANK_HAPOALIM_HEROES_SHARED_PREFERENCES, MODE_PRIVATE)

val Activity.sharedPreferences: SharedPreferences
    get() =
        getSharedPreferences(BANK_HAPOALIM_HEROES_SHARED_PREFERENCES, MODE_PRIVATE)

val Application.sharedPreferences: SharedPreferences
    get() =
        getSharedPreferences(BANK_HAPOALIM_HEROES_SHARED_PREFERENCES, MODE_PRIVATE)

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
