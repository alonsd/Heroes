package com.heroes.ui.application_flow.dashboard.viewmodel

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

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = _uiEvent.asSharedFlow()

    private val _searchState = MutableStateFlow(SearchState("", focused = false, searching = true))
    val searchState = _searchState.asStateFlow()


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
                    if (event.searchText.isEmpty()) { clearListToDefaultState(); return@collect }
                    getHeroesByName(event.searchText)
                }
                UiEvent.ClearQueryClicked -> {
                    clearListToDefaultState()
                }
            }
        }
    }

    private fun clearListToDefaultState() = viewModelScope.launch {
        _searchState.value.query = ""
        val defaultStateList = _uiState.value.modelsListResponse?.subList(0, 5)
        _uiState.emit(UiState(defaultStateList))
    }

    private fun navigateToHeroDetails(heroModel: HeroesListModel) =
        submitAction(UiAction.NavigateToHeroesDetails(heroModel))

    private fun getHeroesByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        _searchState.value = SearchState(name, _searchState.value.focused, true)
        when (val response = heroesRepositoryImpl.getHeroesByNameWithSuggestions(name)) {
            is NetworkResponse.Success -> {
                submitState(UiState(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitState(UiState(errorMessage = message, state = UiState.State.Error))
            }
            else -> {}
        }
    }

    private fun getSuggestedHeroesList() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepositoryImpl.getSuggestedHeroesList(true)) {
            is NetworkResponse.Success -> {
                submitState(UiState(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitState(UiState(errorMessage =  message, state = UiState.State.Error))
            }
            else -> {}
        }
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        _uiAction.emit(uiAction)
    }

    private fun submitState(uiState: UiState) = viewModelScope.launch {
        _uiState.emit(uiState)
        _searchState.value.searching = false
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(uiEvent)
    }

    sealed class UiEvent {
        data class SearchQueryChanged(val searchText: String) : UiEvent()
        data class ListItemClicked(val heroModel: HeroesListModel) : UiEvent()
        object ClearQueryClicked : UiEvent()
    }

    data class UiState(
        val modelsListResponse: List<BaseHeroListModel>? = null,
        val errorMessage: String? = null,
        val state : State = State.Data
    ) {
        enum class State{
            Data,
            Error
        }
    }

    sealed class UiAction {
        data class NavigateToHeroesDetails(val heroModel: HeroesListModel) : UiAction()
    }
}