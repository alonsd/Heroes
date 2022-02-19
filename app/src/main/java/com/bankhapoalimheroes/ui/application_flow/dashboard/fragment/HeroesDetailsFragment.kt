package com.bankhapoalimheroes.ui.application_flow.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bankhapoalimheroes.data.viewmodel.HeroesDetailsViewModel
import com.bankhapoalimheroes.databinding.FragmentHeroDetailsBinding
import org.koin.android.ext.android.inject

class HeroesDetailsFragment : Fragment() {

    //Class Variables - UI
    private lateinit var binding: FragmentHeroDetailsBinding
    private val navArgs: HeroesDetailsFragmentArgs by navArgs()

    //Class Variables - Dependency Injection
    private val heroesDetailsViewModel: HeroesDetailsViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                is HeroesDetailsViewModel.HeroDetailsViewModelActions.ShowHeroDetails -> {
                    Toast.makeText(requireContext(), action.heroDetailsModel.toString(), Toast.LENGTH_SHORT).show()
                }
                is HeroesDetailsViewModel.HeroDetailsViewModelActions.ShowGeneralError -> {
                    Toast.makeText(requireContext(), action.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun init() {
        heroesDetailsViewModel.getHeroDetails(navArgs.heroId)
    }
}