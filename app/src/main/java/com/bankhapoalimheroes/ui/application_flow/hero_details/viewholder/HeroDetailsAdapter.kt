package com.bankhapoalimheroes.ui.application_flow.hero_details.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import com.bankhapoalimheroes.databinding.ViewholderHeroCardBinding
import com.bankhapoalimheroes.model.ui_models.hero_details.HeroDetailsCardModel
import com.bankhapoalimheroes.utils.adapter.DefaultAdapterDiffUtilCallback

class HeroDetailsAdapter : androidx.recyclerview.widget.ListAdapter<HeroDetailsCardModel, HeroDetailsCardViewHolder>(
    DefaultAdapterDiffUtilCallback<HeroDetailsCardModel>()

) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroDetailsCardViewHolder {
        val binding = ViewholderHeroCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroDetailsCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroDetailsCardViewHolder, position: Int) = holder.bind(getItem(position))
}