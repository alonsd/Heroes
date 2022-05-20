package com.heroes.ui.application_flow.dashboard.viewholder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.RecyclerView
import com.heroes.model.ui_models.heroes_list.HeroesListModel

class HeroesListViewHolderCompose(private val composeView: ComposeView) : RecyclerView.ViewHolder(composeView) {

    init {
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
    }

    fun bind(model : HeroesListModel){
        composeView.setContent {

        }
    }
}

@Composable
fun HeroesListViewHolderComp() {
    Box(modifier = Modifier.fillMaxSize()) {
//        Image(painter = rememberImage)
    }
}

@Preview
@Composable
fun HeroesListViewHolderCompPreview() {
    HeroesListViewHolderComp()
}