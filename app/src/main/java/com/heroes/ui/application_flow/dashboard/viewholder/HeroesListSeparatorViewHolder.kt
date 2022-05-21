package com.heroes.ui.application_flow.dashboard.viewholder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.heroes.R
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType

class HeroesListSeparatorViewHolder(
    private val composeView: ComposeView
) : RecyclerView.ViewHolder(composeView.rootView) {

    init {
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
    }

    fun bind(separatorType: HeroListSeparatorModel) {
        composeView.setContent {
            HeroesListSeparatorItem(separatorType)
        }
    }

    @Composable
    fun HeroesListSeparatorItem(separatorType: HeroListSeparatorModel) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
                .padding(16.dp)
        ) {
            val separatorText = when (separatorType.type) {
                HeroesListSeparatorType.SUGGESTIONS -> stringResource(R.string.viewholder_heroes_separator_suggestions)
                HeroesListSeparatorType.SEARCH -> stringResource(R.string.viewholder_heroes_separator_search)
            }
            Text(
                text = separatorText,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}