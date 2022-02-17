package com.bankhapoalimheroes.ui.application_flow.dashboard.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bankhapoalimheroes.databinding.ViewholderHeroesListBinding
import com.bankhapoalimheroes.databinding.ViewholderHeroesSeparatorBinding
import com.bankhapoalimheroes.model.ui_models.heroes_list.BaseHeroListModel
import com.bankhapoalimheroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.bankhapoalimheroes.model.ui_models.heroes_list.HeroesListModel
import com.bankhapoalimheroes.model.ui_models.heroes_list.enums.HeroesListViewHolderType
import com.bankhapoalimheroes.utils.adapter.DefaultAdapterDiffUtilCallback

class HeroesListAdapter(private val onClick: (id: String) -> Unit) : androidx.recyclerview.widget.ListAdapter<BaseHeroListModel,
        RecyclerView.ViewHolder>(DefaultAdapterDiffUtilCallback<BaseHeroListModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HeroesListViewHolderType.HERO.value) {
            val binding = ViewholderHeroesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeroesListViewHolder(binding, onClick)
        }
        val binding = ViewholderHeroesSeparatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroesListSeparatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeroesListViewHolder) {
            return holder.bind(getItem(position) as HeroesListModel)
        }
        return (holder as HeroesListSeparatorViewHolder).bind(getItem(position) as HeroListSeparatorModel)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is HeroListSeparatorModel)
            HeroesListViewHolderType.SEPARATOR.value
        else
            HeroesListViewHolderType.HERO.value
    }
}

