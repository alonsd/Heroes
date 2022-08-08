package com.heroes.ui.application_flow.dashboard.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.heroes.core.SearchBar
import com.heroes.core.SearchState
import com.heroes.databinding.FragmentDashboardBinding
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import com.heroes.utils.extensions.launchAndRepeatWithViewLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalComposeUiApi
class DashboardFragment : Fragment() {

    //Class Variables - UI
    private lateinit var binding: FragmentDashboardBinding

    //Class Variables - Dependency Injection
    private val heroesViewModel: HeroesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSearchBarState()
        observeUiState()
        observeUiAction()
    }

    private fun observeSearchBarState() = launchAndRepeatWithViewLifecycle  {
        binding.heroesSearchView.setContent {

            val progressBarVisible by heroesViewModel.progressbarVisible.collectAsState()
            Log.d("defaultAppDebuger", "progressBarVisible - $progressBarVisible")
            SearchBar(
                searchState = SearchState(TextFieldValue(""), focused = false, searching = progressBarVisible),
                onQueryChanged = { text ->
                    if (text.isEmpty()) return@SearchBar
                    heroesViewModel.submitEvent(HeroesViewModel.UiEvent.SearchQueryChanged(text))
                },
                onSearchFocusChange = { },
                onClearQuery = { },
                onBack = { }
            )
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