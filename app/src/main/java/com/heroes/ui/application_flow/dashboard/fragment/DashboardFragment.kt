package com.heroes.ui.application_flow.dashboard.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.heroes.core.SearchBar
import com.heroes.databinding.FragmentDashboardBinding
import com.heroes.model.ui_models.heroes_list.HeroListSeparatorModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListItem
import com.heroes.ui.application_flow.dashboard.list_items.HeroesListSeparatorItem
import com.heroes.ui.application_flow.dashboard.viewmodel.HeroesViewModel
import com.heroes.utils.extensions.launchAndRepeatWithViewLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.ReadWriteProperty

@ExperimentalComposeUiApi
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            DashboardScreen()
        }
    }
}