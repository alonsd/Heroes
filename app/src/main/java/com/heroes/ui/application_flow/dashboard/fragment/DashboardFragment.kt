package com.heroes.ui.application_flow.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.heroes.databinding.FragmentDashboardBinding
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import com.heroes.utils.custom_implementations.OnSearchViewOnlyTextChangedListener
import com.heroes.utils.extensions.launchAndRepeatWithViewLifecycle
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
        observeUiState()
        observeUiAction()
        observerProgressBarVisible()
    }

    private fun init() {
        binding.heroesSearchView.setOnQueryTextListener(object : OnSearchViewOnlyTextChangedListener() {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) return false
                heroesViewModel.submitEvent(HeroesViewModel.UiEvent.SearchQueryChanged(newText))
                return false
            }
        })
    }

    private fun observerProgressBarVisible() = launchAndRepeatWithViewLifecycle {
        heroesViewModel.progressbarVisible.collect { isVisible ->
            binding.progressBar.isVisible = isVisible
        }
    }

    private fun observeUiAction() = launchAndRepeatWithViewLifecycle {
        heroesViewModel.uiAction.collect { action ->
            when (action) {
                is HeroesViewModel.UiAction.NavigateToHeroesDetails -> {
                    navigateToHeroesDetails(action.heroModel)
                }
            }
        }
    }

    private fun observeUiState() = launchAndRepeatWithViewLifecycle {
        heroesViewModel.uiState.collect { uiAction ->
            when (uiAction) {
                is HeroesViewModel.UiState.Data -> {
                    showHeroesList(uiAction)
                }
                is HeroesViewModel.UiState.Error -> {
                    showGeneralError(uiAction)
                }
                HeroesViewModel.UiState.Initial -> Unit
            }
        }
    }

    private fun navigateToHeroesDetails(heroModel: HeroesListModel) =
        findNavController().navigate(DashboardFragmentDirections.actionMainFragmentToHeroesDetailsFragment(heroModel))


    private fun showHeroesList(result: HeroesViewModel.UiState.Data) {
        binding.heroesList.setContent {
            LazyColumn {
                items(result.modelsListResponse.toList()) { model ->
                    if (model is HeroListSeparatorModel)
                        HeroesListSeparatorItem(model)
                    else if (model is HeroesListModel)
                        HeroesListItem(model) {
                            heroesViewModel.submitEvent(HeroesViewModel.UiEvent.ListItemClicked(model))
                        }
                }
            }
        }
    }

    private fun showGeneralError(result: HeroesViewModel.UiState.Error) {
        Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_LONG).show()
    }

}