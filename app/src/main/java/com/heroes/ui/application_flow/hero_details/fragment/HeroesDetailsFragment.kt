package com.heroes.ui.application_flow.hero_details.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.heroes.R
import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel
import com.heroes.databinding.FragmentHeroDetailsBinding
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.heroes.ui.application_flow.hero_details.list_item.HeroDetailsAdapter
import com.bumptech.glide.Glide
import com.heroes.ui.application_flow.hero_details.list_item.HeroDetailsCardItem
import com.heroes.utils.extensions.setAdapter
import com.heroes.utils.extensions.setVisiblyAsVisible
import org.koin.android.ext.android.inject

class HeroesDetailsFragment : Fragment() {

    //Class Variables - UI
    private lateinit var binding: FragmentHeroDetailsBinding
    private val navArgs: HeroesDetailsFragmentArgs by navArgs()

    //Class Variables - Dependency Injection
    private val heroesDetailsViewModel: HeroesDetailsViewModel by inject()

    //Class Variables - Strings
    private var heroPlaceOfBirth: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHeroDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        handleData()
    }

    private fun handleData() {
        heroesDetailsViewModel.actions.observe(viewLifecycleOwner) { action ->
            when (action) {
                is HeroesDetailsViewModel.HeroDetailsViewModelActions.ShowAdditionalHeroDetails -> {
                    binding.heroDetailsFloatingActionButton.setVisiblyAsVisible()
                    handleAdditionalHeroDetails(action.heroDetailsModel)
                }
                is HeroesDetailsViewModel.HeroDetailsViewModelActions.ShowGeneralError -> {
                    Toast.makeText(requireContext(), action.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun handleAdditionalHeroDetails(heroDetailsModel: HeroDetailsModel) {
        heroPlaceOfBirth = if (heroDetailsModel.placeOfBirth.length < 2) {
            getString(R.string.heroes_details_fragment_not_available)
        } else {
            heroDetailsModel.placeOfBirth
        }
        binding.heroesListHeroPlaceOfBirthTextView.text = getString(
            R.string.hero_details_fragment_place_of_birth,
            heroDetailsModel.placeOfBirth
        )
        binding.heroDetailsList.setContent {
            LazyRow {
                items(heroDetailsModel.heroDetailsCardModels) { heroModel ->
                    HeroDetailsCardItem(model = heroModel)
                }
            }
        }
    }

    private fun init() {
        heroesDetailsViewModel.getAdditionalHeroDetails(navArgs.heroModel.id)
        Glide.with(requireContext()).load(navArgs.heroModel.image)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.heroDetailsHeroImageView)
        binding.heroesListHeroNameTextView.text = navArgs.heroModel.name
        binding.heroDetailsFloatingActionButton.setOnClickListener {
            shareHeroDetails()
        }
    }

    private fun shareHeroDetails() {
        val heroDetailsForSharing = getString(
            R.string.hero_details_fragment_hero_details_for_sharing,
            navArgs.heroModel.name,
            heroPlaceOfBirth,
            navArgs.heroModel.image
        )
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, heroDetailsForSharing)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}