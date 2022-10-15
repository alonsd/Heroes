package com.heroes.ui.application_flow.hero_details.state.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.heroes.R
import com.heroes.core.ui.search.StandardText
import com.heroes.model.ui_models.hero_details.HeroDetailsCardModel
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.heroes.ui.application_flow.hero_details.list_item.HeroDetailsCardItem

@Composable
fun HeroDetailsDataState(
    imageUrl: String,
    heroName: String,
    showHeroPlaceOfBirth: Boolean,
    heroDetailsModel: HeroDetailsModel,
    onFloatingActionButtonClicked: () -> Unit
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onFloatingActionButtonClicked()
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .height(200.dp)
                    .width(150.dp)
            )
            StandardText(
                Modifier.padding(18.dp),
                text = heroName,
            )
            StandardText(
                Modifier.padding(32.dp),
                text = stringResource(
                    id = R.string.hero_details_screen_place_of_birth,
                    if (showHeroPlaceOfBirth.not()) "-" else heroDetailsModel.placeOfBirth
                )
            )
            LazyRow {
                items(heroDetailsModel.heroDetailsCardModels) { heroModel ->
                    HeroDetailsCardItem(model = heroModel)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HeroDetailsDataStatePreview() {
    HeroDetailsDataState("", "Etrigan", true,
        HeroDetailsModel(
            "Hell", listOf(
                HeroDetailsCardModel("Powerstats", "Eye Color", "Red"),
                HeroDetailsCardModel("Appearance", "Durability", "100"),
                HeroDetailsCardModel("Work", "Base", "Hell")
            )
        ),
        onFloatingActionButtonClicked = {}
    )
}
