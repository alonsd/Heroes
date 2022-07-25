package com.heroes.ui.application_flow.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.data.repository.HeroesRepository
import com.heroes.model.ui_models.heroes_list.BaseHeroListModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HeroesViewModel(private val heroesRepository: HeroesRepository) : ViewModel() {

    private val internalUiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = internalUiState.asStateFlow()

    private val internalUiAction = MutableSharedFlow<UiAction>()
    val uiAction = internalUiAction.asSharedFlow()

    private val externalUiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = externalUiEvent.asSharedFlow()

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
                is UiEvent.SearchTextChanged -> {
                    getHeroesByName(event.searchText)
                }
            }
        }
    }

    private fun navigateToHeroDetails(heroModel: HeroesListModel) =
        submitAction(UiAction.NavigateToHeroesDetails(heroModel))

    private fun getHeroesByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepository.getHeroesByNameWithSuggestions(name)) {
            is NetworkResponse.Success -> {
                internalUiState.emit(UiState.Data(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                response.error.message?.let { message ->
                    internalUiState.emit(UiState.Error(message))
                }
            }
            else -> {}
        }
    }

    private fun getSuggestedHeroesList() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepository.getSuggestedHeroesList(true)) {
            is NetworkResponse.Success -> {
                submitState(UiState.Data(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                response.error.message?.let { message ->
                    submitState(UiState.Error(message))
                }
            }
            else -> {}
        }
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        internalUiAction.emit(uiAction)
    }

    private fun submitState(uiState: UiState) = viewModelScope.launch {
        internalUiState.emit(uiState)
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        externalUiEvent.emit(uiEvent)
    }

    sealed class UiEvent {
        data class SearchTextChanged(val searchText: String) : UiEvent()
        data class ListItemClicked(val heroModel: HeroesListModel) : UiEvent()
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