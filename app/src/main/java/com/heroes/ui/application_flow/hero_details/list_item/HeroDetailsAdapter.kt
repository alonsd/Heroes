package com.heroes.ui.application_flow.hero_details.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heroes.databinding.ViewholderHeroCardBinding
import com.heroes.model.ui_models.hero_details.HeroDetailsCardModel
import com.heroes.utils.adapter.DefaultAdapterDiffUtilCallback

class HeroDetailsAdapter : androidx.recyclerview.widget.ListAdapter<HeroDetailsCardModel, HeroDetailsCardViewHolder>(
    DefaultAdapterDiffUtilCallback<HeroDetailsCardModel>()

) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroDetailsCardViewHolder {
        val binding = ViewholderHeroCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroDetailsCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroDetailsCardViewHolder, position: Int) = holder.bind(getItem(position))
}