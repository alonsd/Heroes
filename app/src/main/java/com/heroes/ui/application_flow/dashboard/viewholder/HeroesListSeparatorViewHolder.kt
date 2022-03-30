package com.heroes.ui.application_flow.dashboard.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.heroes.R
import com.heroes.databinding.ViewholderHeroesSeparatorBinding
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType

class HeroesListSeparatorViewHolder(private val binding : ViewholderHeroesSeparatorBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(separatorType : HeroListSeparatorModel){
        binding.suggestionsTextView.text = when(separatorType.type) {
            HeroesListSeparatorType.SUGGESTIONS -> binding.root.context.getString(R.string.viewholder_heroes_separator_suggestions)
            HeroesListSeparatorType.SEARCH -> binding.root.context.getString(R.string.viewholder_heroes_separator_search)
        }
    }

}