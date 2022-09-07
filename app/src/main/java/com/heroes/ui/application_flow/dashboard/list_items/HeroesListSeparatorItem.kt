package com.heroes.ui.application_flow.dashboard.list_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heroes.R
import com.heroes.model.ui_models.heroes_list.HeroSeparatorModel
import com.heroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType


@Composable
fun HeroesListSeparatorItem(separatorType: HeroSeparatorModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
    ) {
        val separatorText = when (separatorType.type) {
            HeroesListSeparatorType.SUGGESTIONS -> stringResource(R.string.list_item_heroes_separator_suggestions)
            HeroesListSeparatorType.SEARCH -> stringResource(R.string.list_item_heroes_separator_search)
        }
        Text(
            text = separatorText,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
