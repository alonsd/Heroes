package com.heroes.ui.application_flow.dashboard.viewmodel

import androidx.compose.runtime.RecomposeScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.core.SearchState
import com.heroes.data.repository.HeroesRepositoryImpl
import com.heroes.model.ui_models.heroes_list.BaseHeroListModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HeroesViewModel(private val heroesRepositoryImpl: HeroesRepositoryImpl) : ViewModel() {

    private val internalUiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = internalUiState.asStateFlow()

    private val internalUiAction = MutableSharedFlow<UiAction>()
    val uiAction = internalUiAction.asSharedFlow()

    private val mutableUiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = mutableUiEvent.asSharedFlow()

    private val internalSearchState = MutableStateFlow(SearchState("", focused = false, searching = false))
    val searchState = internalSearchState.asStateFlow()

//    private val mutableSearchText = MutableStateFlow("")
//    private val searchText = mutableSearchText.asStateFlow().debounce(300)


    init {
        observeUiEvents()
//        observeSearchText()
        getSuggestedHeroesList()
    }

//    private fun observeSearchText() = viewModelScope.launch {
//        searchText.collect { text ->
//            getHeroesByName(text)
//        }
//    }

    private fun observeUiEvents() = viewModelScope.launch {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ListItemClicked -> {
                    navigateToHeroDetails(event.heroModel)
                }
                is UiEvent.SearchQueryChanged -> {
//                    mutableSearchText.value = event.searchText
                    flow { emit(event.searchText) }.debounce(1000).collect { searchText ->
                        getHeroesByName(searchText)
                    }

                }
                UiEvent.ClearQueryClicked -> {
                    clearSearchText()
                }
            }
        }
    }

    private fun clearSearchText() {
        internalSearchState.value.query = ""
    }

    private fun navigateToHeroDetails(heroModel: HeroesListModel) =
        submitAction(UiAction.NavigateToHeroesDetails(heroModel))

    private fun getHeroesByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        internalSearchState.value = SearchState(name, internalSearchState.value.focused, true)
        when (val response = heroesRepositoryImpl.getHeroesByNameWithSuggestions(name)) {
            is NetworkResponse.Success -> {
                submitState(UiState.Data(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitState(UiState.Error(message))
            }
            else -> {}
        }
    }

    private fun getSuggestedHeroesList() = viewModelScope.launch(Dispatchers.IO) {
        internalSearchState.value.searching = true
        when (val response = heroesRepositoryImpl.getSuggestedHeroesList(true)) {
            is NetworkResponse.Success -> {
                submitState(UiState.Data(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitState(UiState.Error(message))
            }
            else -> {}
        }
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        internalUiAction.emit(uiAction)
    }

    private fun submitState(uiState: UiState) = viewModelScope.launch {
        internalUiState.emit(uiState)
        internalSearchState.value.searching = false
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        mutableUiEvent.emit(uiEvent)
    }

    sealed class UiEvent {
        data class SearchQueryChanged(val searchText: String) : UiEvent()
        data class ListItemClicked(val heroModel: HeroesListModel) : UiEvent()
        object ClearQueryClicked : UiEvent()
    }

    sealed class UiState {
        data class Data(val modelsListResponse: List<BaseHeroListModel>) : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Initial : UiState()
    }

    sealed class UiAction {
        data class NavigateToHeroesDetails(val heroModel: HeroesListModel) : UiAction()
    }
}