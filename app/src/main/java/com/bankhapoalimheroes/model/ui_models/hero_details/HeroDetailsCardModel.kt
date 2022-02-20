package com.bankhapoalimheroes.model.ui_models.hero_details

import com.bankhapoalimheroes.utils.adapter.DefaultAdapterDiffUtilCallback

data class HeroDetailsCardModel(val detailsTitle: String, val subDetailsTitle: String, val subDetailsValue: String) : DefaultAdapterDiffUtilCallback.ModelWithId {
    override fun fetchId(): String = detailsTitle
}