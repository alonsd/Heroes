package com.heroes.model.ui_models.heroes_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroModel(val id: String, val name: String, val image: String) : BaseHeroModel(), Parcelable