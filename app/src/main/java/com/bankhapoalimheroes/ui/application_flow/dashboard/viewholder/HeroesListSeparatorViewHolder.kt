package com.bankhapoalimheroes.ui.application_flow.dashboard.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bankhapoalimheroes.R
import com.bankhapoalimheroes.databinding.ViewholderHeroesSeparatorBinding
import com.bankhapoalimheroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.bankhapoalimheroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType

class HeroesListSeparatorViewHolder(private val binding : ViewholderHeroesSeparatorBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(separatorType : HeroListSeparatorModel){
        binding.suggestionsTextView.text = when(separatorType.type) {
            HeroesListSeparatorType.SUGGESTIONS -> binding.root.context.getString(R.string.viewholder_heroes_separator_suggestions)
            HeroesListSeparatorType.SEARCH -> binding.root.context.getString(R.string.viewholder_heroes_separator_search)
        }
    }

}