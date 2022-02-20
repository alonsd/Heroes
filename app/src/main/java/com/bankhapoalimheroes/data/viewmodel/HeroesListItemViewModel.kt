package com.bankhapoalimheroes.data.viewmodel

import androidx.lifecycle.ViewModel
import com.bankhapoalimheroes.utils.application.App
import com.bankhapoalimheroes.utils.constants.CacheConstants
import com.bankhapoalimheroes.utils.extensions.imagesCacheTime
import com.bankhapoalimheroes.utils.extensions.sharedPreferences

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