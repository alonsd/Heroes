package com.bankhapoalimheroes.model.ui_models.heroes_list

import com.bankhapoalimheroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType
import com.bankhapoalimheroes.utils.adapter.DefaultAdapterDiffUtilCallback

data class HeroListSeparatorModel(val type: HeroesListSeparatorType) : BaseHeroListModel(), DefaultAdapterDiffUtilCallback.ModelWithId {
    override fun fetchId(): String = type.name
}