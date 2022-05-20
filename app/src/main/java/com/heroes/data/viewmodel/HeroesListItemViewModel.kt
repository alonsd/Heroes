package com.heroes.data.viewmodel

import androidx.lifecycle.ViewModel
import com.heroes.utils.application.App
import com.heroes.utils.constants.CacheConstants
import com.heroes.utils.extensions.imagesCacheTime
import com.heroes.utils.extensions.sharedPreferences

class HeroesListItemViewModel : ViewModel() {

    /**
     * We update the last image cache time if 1 day has passed, using it as
     * a signature data that changes after 1 day causing as to re-fetch the image and for the old image to
     * be invalidated by Glides default behavior.
     */
    fun updateLastImagesCacheTime() {
        if (App.instance.sharedPreferences.imagesCacheTime == 0L) {
            App.instance.sharedPreferences.imagesCacheTime = System.currentTimeMillis()
        }
        if (System.currentTimeMillis() - App.instance.sharedPreferences.imagesCacheTime >= CacheConstants.ONE_DAY_MILLIS) {
            App.instance.sharedPreferences.imagesCacheTime = System.currentTimeMillis()
        }
    }

}