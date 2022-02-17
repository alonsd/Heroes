package com.bankhapoalimheroes.ui.application_flow.dashboard.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bankhapoalimheroes.R
import com.bankhapoalimheroes.databinding.ViewholderHeroesListBinding
import com.bankhapoalimheroes.model.ui_models.heroes_list.HeroesListModel
import com.bumptech.glide.Glide

class HeroesListViewHolder(
    private val binding: ViewholderHeroesListBinding,
    private val onClick: (heroId: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HeroesListModel) {
        binding.heroName.text = model.name
        Glide.with(binding.root.context).load(model.image).placeholder(R.mipmap.ic_launcher).into(binding.heroImageView)
        binding.root.setOnClickListener { onClick(model.id) }
    }
}