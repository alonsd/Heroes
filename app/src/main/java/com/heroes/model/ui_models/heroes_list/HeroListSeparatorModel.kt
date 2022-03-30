package com.heroes.model.ui_models.heroes_list

import com.heroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType
import com.heroes.utils.adapter.DefaultAdapterDiffUtilCallback

data class HeroListSeparatorModel(val type: HeroesListSeparatorType) : BaseHeroListModel(), DefaultAdapterDiffUtilCallback.ModelWithId {
    override fun fetchId(): String = type.name
}