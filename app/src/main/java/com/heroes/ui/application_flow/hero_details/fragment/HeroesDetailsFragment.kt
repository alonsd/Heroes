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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.navArgs
import com.heroes.R
import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel
import com.heroes.databinding.FragmentHeroDetailsBinding
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.bumptech.glide.Glide
import com.heroes.ui.application_flow.hero_details.list_item.HeroDetailsCardItem
import com.heroes.utils.extensions.launchAndRepeatWithViewLifecycle
import com.heroes.utils.extensions.setVisiblyAsVisible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

class HeroesDetailsFragment : Fragment() {

    //Class Variables - UI
    private lateinit var binding: FragmentHeroDetailsBinding
    private val navArgs: HeroesDetailsFragmentArgs by navArgs()

    //Class Variables - Dependency Injection
    private val heroesDetailsViewModel: HeroesDetailsViewModel by stateViewModel( state = { navArgs.toBundle() })

    //Class Variables - Strings
    private var heroPlaceOfBirth: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHeroDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initListeners()
        observeUiState()
    }

    private fun observeUiState() = launchAndRepeatWithViewLifecycle {
        heroesDetailsViewModel.uiState.collect { state ->
            when (state) {
                is HeroesDetailsViewModel.UiState.Data -> {
                    binding.heroDetailsFloatingActionButton.setVisiblyAsVisible()
                    handleAdditionalHeroDetails(state.heroDetailsModel)
                }
                is HeroesDetailsViewModel.UiState.Error -> {
                    Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
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
        Glide.with(requireContext()).load(navArgs.heroModel.image)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.heroDetailsHeroImageView)
        binding.heroesListHeroNameTextView.text = navArgs.heroModel.name
    }

    private fun initListeners() {
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