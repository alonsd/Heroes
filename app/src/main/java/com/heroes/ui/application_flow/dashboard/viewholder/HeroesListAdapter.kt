package com.heroes.ui.application_flow.dashboard.viewholder

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heroes.model.ui_models.heroes_list.BaseHeroListModel
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.model.ui_models.heroes_list.enums.HeroesListViewHolderType
import com.heroes.utils.adapter.DefaultAdapterDiffUtilCallback

class HeroesListAdapter(
    private val onClick: (heroModel: HeroesListModel) -> Unit
) : ListAdapter<BaseHeroListModel, RecyclerView.ViewHolder>(
    DefaultAdapterDiffUtilCallback<BaseHeroListModel>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HeroesListViewHolderType.HERO.value) {
            return HeroesListViewHolderCompose(ComposeView(parent.context), onClick)
        }
        return HeroesListSeparatorViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeroesListSeparatorViewHolder -> {
                holder.bind(getItem(position) as HeroListSeparatorModel)
            }
            is HeroesListViewHolderCompose -> {
                holder.bind(getItem(position) as HeroesListModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is HeroListSeparatorModel)
            HeroesListViewHolderType.SEPARATOR.value
        else
            HeroesListViewHolderType.HERO.value
    }
}

