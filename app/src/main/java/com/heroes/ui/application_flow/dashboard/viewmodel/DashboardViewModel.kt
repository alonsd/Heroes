package com.heroes.ui.application_flow.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.core.ui.search.SearchState
import com.heroes.data.repository.HeroesRepositoryImpl
import com.heroes.model.ui_models.heroes_list.BaseHeroModel
import com.heroes.model.ui_models.heroes_list.HeroModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(private val heroesRepositoryImpl: HeroesRepositoryImpl) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction = Channel<UiAction>()
    val uiAction = _uiAction.receiveAsFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = _uiEvent.asSharedFlow()

    private val _searchState = MutableStateFlow(SearchState("", focused = false, searching = true))
    val searchState = _searchState.asStateFlow()

    private val suggestedHeroes = mutableListOf<BaseHeroModel>()


    init {
        observeUiEvents()
        getSuggestedHeroesList()
    }

    private fun observeUiEvents() = viewModelScope.launch {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ListItemClicked -> {
                    navigateToHeroDetails(event.heroModel)
                }
                is UiEvent.SearchQueryChanged -> {
                    if (event.searchText.isEmpty()) {
                        clearListToDefaultState(); return@collect
                    }
                    getHeroesByName(event.searchText)
                }
                is UiEvent.SearchBarFocusChanged -> {
                    submitSearchState(SearchState(_searchState.value.query, event.searchBarFocused, _searchState.value.searching))
                }
                is UiEvent.ListIsScrolling -> {
                    val searchStateFocused = _searchState.value.focused
                    if (searchStateFocused.not() || event.listIsScrolling.not()) return@collect
                    submitSearchState(SearchState(_searchState.value.query, false, _searchState.value.searching))
                }

                UiEvent.ClearQueryClicked -> {
                    clearListToDefaultState()
                }
            }
        }
    }

    private fun submitSearchState(searchState: SearchState) = viewModelScope.launch {
        _searchState.emit(searchState)
    }


    private fun clearListToDefaultState() = viewModelScope.launch {
        submitSearchState(SearchState("", _searchState.value.focused, false))
        _uiState.emit(UiState(state = UiState.State.Data, heroesList = suggestedHeroes))
    }

    private fun navigateToHeroDetails(heroModel: HeroModel) =
        submitAction(UiAction.NavigateToHeroesDetails(heroModel))

    private fun getHeroesByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        submitSearchState(SearchState(name, _searchState.value.focused, true))
        when (val response = heroesRepositoryImpl.getHeroesByNameWithSuggestions(name)) {
            is NetworkResponse.Success -> {
                val heroesList = response.body as List<HeroModel>
                if (heroesList.isEmpty()) {
                    clearListToDefaultState()
                    return@launch
                }
                submitUiState(UiState(state = UiState.State.Data, heroesList = heroesList))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitUiState(UiState(errorMessage = message, state = UiState.State.Error))
            }
            else -> {}
        }
    }

    private fun getSuggestedHeroesList() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepositoryImpl.getSuggestedHeroesList(true)) {
            is NetworkResponse.Success -> {
                val heroesList = response.body as List<HeroModel>
                suggestedHeroes.addAll(heroesList)
                submitUiState(UiState(state = UiState.State.Data, heroesList = heroesList))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitUiState(UiState(errorMessage = message, state = UiState.State.Error))
            }
            else -> {}
        }
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        _uiAction.send(uiAction)
    }

    private fun submitUiState(uiState: UiState) = viewModelScope.launch {
        _uiState.emit(uiState)
        submitSearchState(SearchState(_searchState.value.query, _searchState.value.focused, false))
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(uiEvent)
    }

    sealed class UiEvent {
        data class SearchQueryChanged(val searchText: String) : UiEvent()
        data class SearchBarFocusChanged(val searchBarFocused: Boolean) : UiEvent()
        data class ListIsScrolling(val listIsScrolling: Boolean) : UiEvent()
        data class ListItemClicked(val heroModel: HeroModel) : UiEvent()
        object ClearQueryClicked : UiEvent()
    }

    data class UiState(
        val heroesList: List<BaseHeroModel> = emptyList(),
        val errorMessage: String = "",
        val state: State = State.Initial
    ) {
        enum class State {
            Data,
            Error,
            Initial
        }
    }

    sealed class UiAction {
        data class NavigateToHeroesDetails(val heroModel: HeroModel) : UiAction()
    }
}