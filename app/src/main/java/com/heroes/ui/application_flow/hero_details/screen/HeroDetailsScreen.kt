package com.heroes.ui.application_flow.hero_details.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.heroes.R
import com.heroes.core.StandardText
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.hero_details.list_item.HeroDetailsCardItem
import com.heroes.ui.application_flow.hero_details.viewmodel.HeroesDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get


@Destination
@Composable
fun HeroDetailsScreen(
    model: HeroesListModel,
    viewModel: HeroesDetailsViewModel = get()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
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
        if (uiState is HeroesDetailsViewModel.UiState.Data) {
            val data = uiState as HeroesDetailsViewModel.UiState.Data
            StandardText(
                Modifier.padding(18.dp),
                text = model.name,
            )
            StandardText(
                Modifier.padding(32.dp),
                text = stringResource(
                    id = R.string.hero_details_fragment_place_of_birth,
                    //TODO - fix this impl once VM is refactored
                    data.heroDetailsModel.placeOfBirth
                )
            )

            LazyRow {
                items(data.heroDetailsModel.heroDetailsCardModels) { heroModel ->
                    HeroDetailsCardItem(model = heroModel)
                }
            }
        }
    }
}

@Preview
@Composable
fun HeroDetailsScreenPreview() {
//    HeroDetailsScreen()
}