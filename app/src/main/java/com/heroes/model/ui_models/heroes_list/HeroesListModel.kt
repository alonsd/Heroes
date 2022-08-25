package com.heroes.model.ui_models.heroes_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroesListModel(val id: String, val name: String, val image: String) : BaseHeroListModel(), Parcelable