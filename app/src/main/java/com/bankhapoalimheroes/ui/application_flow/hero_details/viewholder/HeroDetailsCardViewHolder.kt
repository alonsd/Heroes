package com.bankhapoalimheroes.ui.application_flow.hero_details.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bankhapoalimheroes.R
import com.bankhapoalimheroes.databinding.ViewholderHeroCardBinding
import com.bankhapoalimheroes.model.ui_models.hero_details.HeroDetailsCardModel

class HeroDetailsCardViewHolder(private val binding: ViewholderHeroCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HeroDetailsCardModel) {
        binding.heroCardTitle.text = model.detailsTitle
        binding.heroCardDescription.text = binding.root.context.getString(
            R.string.viewholder_hero_card_description,
            model.subDetailsTitle, model.subDetailsValue
        )
    }
}