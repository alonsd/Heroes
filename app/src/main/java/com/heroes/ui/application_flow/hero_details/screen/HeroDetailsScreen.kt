package com.heroes.ui.application_flow.hero_details.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.heroes.R
import com.heroes.core.extensions.shareInformationAsText
import com.heroes.core.ui.loading.GeneralLoadingState
import com.heroes.model.ui_models.heroes_list.HeroModel
import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder


@Destination
@Composable
fun HeroDetailsScreen(
    model: HeroModel,
    viewModel: HeroesDetailsViewModel = koinViewModel(parameters = { ParametersHolder(mutableListOf(model)) })
) {

    val uiState by viewModel.uiState.collectAsState()
    val uiAction by viewModel.uiAction.collectAsState(initial = null)

    when (uiAction) {
        is HeroesDetailsViewModel.UiAction.ShareHeroesDetails -> {
            if (uiState.state != HeroesDetailsViewModel.UiState.State.Data) return
            val context = LocalContext.current
            val heroDetailsForSharing = stringResource(
                R.string.hero_details_fragment_hero_details_for_sharing,
                model.name,
                uiState.heroDetailsModel.placeOfBirth,
                model.image
            )
            shareInformationAsText(heroDetailsForSharing, context)
        }
        null -> Unit
    }

    when (uiState.state) {
        HeroesDetailsViewModel.UiState.State.Data -> {
            HeroDetailsDataState(model, uiState, viewModel)
        }
        HeroesDetailsViewModel.UiState.State.Error -> {

        }
        HeroesDetailsViewModel.UiState.State.Initial -> {
            GeneralLoadingState()
        }
    }
}


@Preview
@Composable
fun HeroDetailsScreenPreview() {
    HeroDetailsScreen(HeroModel("12345", "Ethan Hunt", "https://www.superherodb.com/pictures2/portraits/10/100/10476.jpg"))
}