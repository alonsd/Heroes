package com.heroes.ui.screens.hero_details.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.heroes.R
import com.heroes.core.extensions.shareInformationAsText
import com.heroes.model.ui_models.heroes_list.HeroModel
import com.heroes.ui.screens.hero_details.state.data.HeroDetailsDataState
import com.heroes.ui.screens.hero_details.state.loading.HeroDetailsLoadingState
import com.heroes.ui.screens.hero_details.viewmodel.HeroesDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination


@Destination(
    navArgsDelegate = HeroModel::class
)
@Composable
fun HeroDetailsScreen(
    viewModel : HeroesDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiAction by viewModel.uiAction.collectAsState(initial = null)

    when (uiAction) {
        is HeroesDetailsViewModel.UiAction.ShareHeroesDetails -> {
            if (uiState.state != HeroesDetailsViewModel.UiState.State.Data) return
            val context = LocalContext.current
            val heroDetailsForSharing = stringResource(
                R.string.hero_details_screen_hero_details_for_sharing,
                uiState.heroName,
                uiState.heroDetailsModel.placeOfBirth,
                uiState.heroImage
            )
            shareInformationAsText(heroDetailsForSharing, context)
        }
        null -> Unit
    }

    when (uiState.state) {
        HeroesDetailsViewModel.UiState.State.Data -> {
            HeroDetailsDataState(
                uiState.heroImage,
                uiState.heroName,
                uiState.showHeroPlaceOfBirth,
                uiState.heroDetailsModel,
                onFloatingActionButtonClicked = {
                    viewModel.submitEvent(HeroesDetailsViewModel.UiEvent.FloatingActionButtonClicked)
                }
            )
        }
        HeroesDetailsViewModel.UiState.State.Error -> {

        }
        HeroesDetailsViewModel.UiState.State.Initial -> {
            HeroDetailsLoadingState()
        }
    }
}