package com.heroes.model.ui_models.heroes_list

import android.os.Parcelable
import com.heroes.utils.adapter.DefaultAdapterDiffUtilCallback
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroesListModel(val id: String, val name: String, val image: String) : BaseHeroListModel(),
    DefaultAdapterDiffUtilCallback.ModelWithId, Parcelable {

    override fun fetchId(): String = id
}