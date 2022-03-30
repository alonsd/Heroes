package com.heroes.data.viewmodel

import androidx.lifecycle.ViewModel
import com.heroes.utils.application.App
import com.heroes.utils.constants.CacheConstants
import com.heroes.utils.extensions.imagesCacheTime
import com.heroes.utils.extensions.sharedPreferences

class HeroesListItemViewModel : ViewModel() {

    fun updateLastImagesCacheTime() {
        if (App.instance.sharedPreferences.imagesCacheTime == 0L) {
            App.instance.sharedPreferences.imagesCacheTime = System.currentTimeMillis()
        }
        if (System.currentTimeMillis() - App.instance.sharedPreferences.imagesCacheTime >= CacheConstants.ONE_DAY_MILLIS) {
            App.instance.sharedPreferences.imagesCacheTime = System.currentTimeMillis()
        }
    }

}