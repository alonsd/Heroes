package com.bankhapoalimheroes.ui.application_flow.dashboard.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import com.bankhapoalimheroes.databinding.ViewholderHeroesListBinding
import com.bankhapoalimheroes.model.ui_models.HeroesListModel
import com.bankhapoalimheroes.utils.adapter.DefaultAdapterDiffUtilCallback

class HeroesListAdapter(private val onClick : (id : String) -> Unit) : androidx.recyclerview.widget.ListAdapter<HeroesListModel,
        HeroesListViewHolder>(DefaultAdapterDiffUtilCallback<HeroesListModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesListViewHolder {
        val binding = ViewholderHeroesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroesListViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: HeroesListViewHolder, position: Int) = holder.bind(getItem(position))
}