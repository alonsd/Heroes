package com.heroes.ui.application_flow.hero_details.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.heroes.R
import com.heroes.core.ui.search.StandardText
import com.heroes.model.ui_models.heroes_list.HeroModel
import com.heroes.ui.application_flow.hero_details.list_item.HeroDetailsCardItem
import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel

@Composable
fun HeroDetailsDataState(model: HeroModel, uiState: HeroesDetailsViewModel.UiState, viewModel: HeroesDetailsViewModel) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.submitEvent(HeroesDetailsViewModel.UiEvent.FloatingActionButtonClicked)
        }) {
            Icon(Icons.Filled.Add, "")
        }
    }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = model.image),
                contentDescription = null,
                Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            )
            StandardText(
                Modifier.padding(18.dp),
                text = model.name,
            )
            if (uiState.showHeroPlaceOfBirth) {
                StandardText(
                    Modifier.padding(32.dp),
                    text = stringResource(
                        id = R.string.hero_details_screen_place_of_birth,
                        uiState.heroDetailsModel.placeOfBirth
                    )
                )
            }
            LazyRow {
                items(uiState.heroDetailsModel.heroDetailsCardModels) { heroModel ->
                    HeroDetailsCardItem(model = heroModel)
                }
            }
        }
    }
}
