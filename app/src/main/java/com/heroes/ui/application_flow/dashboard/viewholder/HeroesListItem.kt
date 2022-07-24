package com.heroes.ui.application_flow.dashboard.viewholder

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.heroes.model.ui_models.heroes_list.HeroesListModel

@Composable
fun HeroesListItem(model: HeroesListModel, onClick: (heroModel: HeroesListModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick(model)
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model.image),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
        )
        Text(
            text = model.name,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
    }
}


