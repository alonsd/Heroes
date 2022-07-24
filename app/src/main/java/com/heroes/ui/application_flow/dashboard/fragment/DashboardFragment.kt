package com.heroes.ui.application_flow.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import com.heroes.databinding.FragmentDashboardBinding
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.dashboard.viewholder.HeroesListItem
import com.heroes.ui.application_flow.dashboard.viewholder.HeroesListSeparatorItem
import com.heroes.utils.custom_implementations.OnSearchViewOnlyTextChangedListener
import com.heroes.utils.extensions.setVisiblyAsGone
import com.heroes.utils.extensions.setVisiblyAsVisible
import org.koin.android.ext.android.get

class DashboardFragment : Fragment() {

    //Class Variables - UI
    private lateinit var binding: FragmentDashboardBinding

    //Class Variables - Dependency Injection
    private val heroesViewModel = get<HeroesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        handleData()
    }

    private fun init() {
        binding.heroesSearchView.setOnQueryTextListener(object : OnSearchViewOnlyTextChangedListener() {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) return false
                heroesViewModel.getHeroesByName(newText)
                binding.progressBar.setVisiblyAsVisible()
                return false
            }

        })
    }

    private fun handleData() = heroesViewModel.actions.observe(viewLifecycleOwner) { result ->
        when (result) {
            is HeroesViewModel.HeroesViewModelActions.ShowHeroesList -> {
                showHeroesList(result)
            }
            is HeroesViewModel.HeroesViewModelActions.ShowGeneralError -> {
                showGeneralError(result)
            }

            is HeroesViewModel.HeroesViewModelActions.GetSuggestedList -> {
                getSuggestedHeroesList()
            }
            else -> return@observe
        }
    }

    private fun showHeroesList(result: HeroesViewModel.HeroesViewModelActions.ShowHeroesList) {
        binding.heroesRecyclerView2.setContent {
            LazyColumn {
                items(result.modelsListResponse.toList()) { model ->
                    if (model is HeroListSeparatorModel)
                        HeroesListSeparatorItem(model)
                    else if (model is HeroesListModel)
                        HeroesListItem(model) {
                            findNavController().navigate(DashboardFragmentDirections.actionMainFragmentToHeroesDetailsFragment(model))
                        }
                }
            }
        }
        binding.progressBar.setVisiblyAsGone()
    }

    private fun showGeneralError(result: HeroesViewModel.HeroesViewModelActions.ShowGeneralError) {
        Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_LONG).show()
        binding.progressBar.setVisiblyAsGone()
    }

    private fun getSuggestedHeroesList() {
        heroesViewModel.getSuggestedHeroesList()
        binding.progressBar.setVisiblyAsVisible()
    }

}