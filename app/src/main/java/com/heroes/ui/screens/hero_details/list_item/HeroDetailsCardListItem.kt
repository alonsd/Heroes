package com.heroes.ui.screens.hero_details.list_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heroes.R
import com.heroes.model.ui_models.hero_details.HeroDetailsCardModel


@Preview
@Composable
fun HeroDetailsCardItemPreview() {
    HeroDetailsCardItem(
        HeroDetailsCardModel(
            "Appearance", "Eye Color",
            "Red"
        )
    )
}

@Composable
fun HeroDetailsCardItem(model: HeroDetailsCardModel) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(150.dp)
            .padding(15.dp),
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(15.dp),
                text = model.detailsTitle,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(15.dp),
                text = stringResource(
                    id = R.string.list_item_hero_card_description,
                    model.subDetailsTitle, model.subDetailsValue
                ),
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}