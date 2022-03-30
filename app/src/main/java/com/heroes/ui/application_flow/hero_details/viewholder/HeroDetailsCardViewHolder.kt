package com.heroes.ui.application_flow.hero_details.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.heroes.R
import com.heroes.databinding.ViewholderHeroCardBinding
import com.heroes.model.ui_models.hero_details.HeroDetailsCardModel

class HeroDetailsCardViewHolder(private val binding: ViewholderHeroCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HeroDetailsCardModel) {
        binding.heroCardTitle.text = model.detailsTitle
        binding.heroCardDescription.text = binding.root.context.getString(
            R.string.viewholder_hero_card_description,
            model.subDetailsTitle, model.subDetailsValue
        )
    }
}