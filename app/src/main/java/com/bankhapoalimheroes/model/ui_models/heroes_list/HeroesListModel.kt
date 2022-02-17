package com.bankhapoalimheroes.model.ui_models.heroes_list

import com.bankhapoalimheroes.utils.adapter.DefaultAdapterDiffUtilCallback

data class HeroesListModel(val id: String, val name: String, val image: String) : BaseHeroListModel(),
    DefaultAdapterDiffUtilCallback.ModelWithId {

    override fun fetchId(): String = id
}