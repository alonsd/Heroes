package com.bankhapoalimheroes.ui.application_flow.dashboard.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bankhapoalimheroes.R
import com.bankhapoalimheroes.data.viewmodel.HeroesListItemViewModel
import com.bankhapoalimheroes.databinding.ViewholderHeroesListBinding
import com.bankhapoalimheroes.model.ui_models.heroes_list.HeroesListModel
import com.bankhapoalimheroes.utils.application.App
import com.bankhapoalimheroes.utils.constants.CacheConstants.ONE_DAY_MILLIS
import com.bankhapoalimheroes.utils.extensions.imagesCacheTime
import com.bankhapoalimheroes.utils.extensions.sharedPreferences
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

        /*We update the last image cache time if 1 day has passed, using it as
        a signature data that changes after 1 day causing as to re-fetch the image and for the
        old image to be invalidated by Glides default behavior.*/
        heroesListItemViewModel.updateLastImagesCacheTime()
        Glide.with(binding.root.context).load(model.image)
            .signature(ObjectKey(App.instance.sharedPreferences.imagesCacheTime))
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.heroesListHeroImageView)
        binding.root.setOnClickListener { onClick(model) }
    }
}