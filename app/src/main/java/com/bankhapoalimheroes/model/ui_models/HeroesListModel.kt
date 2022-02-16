package com.bankhapoalimheroes.model.ui_models

import com.bankhapoalimheroes.utils.adapter.DefaultAdapterDiffUtilCallback

data class HeroesListModel(val id: String, val name: String, val image: String) : DefaultAdapterDiffUtilCallback.ModelWithId {

    override fun fetchId(): String = id
}