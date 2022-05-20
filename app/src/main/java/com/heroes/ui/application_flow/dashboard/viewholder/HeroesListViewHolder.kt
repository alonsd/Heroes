package com.heroes.ui.application_flow.dashboard.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.heroes.R
import com.heroes.data.viewmodel.HeroesListItemViewModel
import com.heroes.databinding.ViewholderHeroesListBinding
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.utils.application.App
import com.heroes.utils.extensions.imagesCacheTime
import com.heroes.utils.extensions.sharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class HeroesListViewHolder(
    private val binding: ViewholderHeroesListBinding,
    private val onClick: (heroModel: HeroesListModel) -> Unit
) : RecyclerView.ViewHolder(binding.root), KoinComponent {

    //Class Variables - Dependency Injection
    private val heroesListItemViewModel : HeroesListItemViewModel by inject()

    fun bind(model: HeroesListModel) {

        binding.heroesListHeroName.text = model.name
        heroesListItemViewModel.updateLastImagesCacheTime()
        Glide.with(binding.root.context).load(model.image)
            .signature(ObjectKey(App.instance.sharedPreferences.imagesCacheTime))
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.heroesListHeroImageView)
        binding.root.setOnClickListener { onClick(model) }
    }
}