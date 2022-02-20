package com.bankhapoalimheroes.ui.application_flow.hero_details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bankhapoalimheroes.R
import com.bankhapoalimheroes.data.viewmodel.HeroesDetailsViewModel
import com.bankhapoalimheroes.databinding.FragmentHeroDetailsBinding
import com.bankhapoalimheroes.model.ui_models.hero_details.HeroDetailsModel
import com.bankhapoalimheroes.ui.application_flow.hero_details.viewholder.HeroDetailsAdapter
import com.bankhapoalimheroes.utils.extensions.setAdapter
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class HeroesDetailsFragment : Fragment() {

    //Class Variables - UI
    private lateinit var binding: FragmentHeroDetailsBinding
    private val navArgs: HeroesDetailsFragmentArgs by navArgs()

    //Class Variables - Dependency Injection
    private val heroesDetailsViewModel: HeroesDetailsViewModel by inject()

    //Class Variables - Adapter
    private var adapter = HeroDetailsAdapter()

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
        heroesDetailsViewModel.actions.observe(requireActivity()) { action ->
            when (action) {
                is HeroesDetailsViewModel.HeroDetailsViewModelActions.ShowAdditionalHeroDetails -> {
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
        binding.heroesListHeroPlaceOfBirthTextView.text = getString(R.string.hero_details_fragment_place_of_birth,
            heroDetailsModel.placeOfBirth)
        binding.heroDetailsRecyclerView.setAdapter(requireContext(), adapter, true)
        adapter.submitList(heroDetailsModel.heroDetailsCardModels.toList())
    }

    private fun init() {
        heroesDetailsViewModel.getAdditionalHeroDetails(navArgs.heroModel.id)
        Glide.with(requireContext()).load(navArgs.heroModel.image)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.heroDetailsHeroImageView)
        binding.heroesListHeroNameTextView.text = navArgs.heroModel.name
    }
}