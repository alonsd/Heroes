package com.heroes.ui.application_flow.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.core.ui.search.SearchState
import com.heroes.data.repository.HeroesRepository
import com.heroes.model.ui_models.heroes_list.BaseHeroModel
import com.heroes.model.ui_models.heroes_list.HeroModel
import com.heroes.model.ui_models.heroes_list.HeroSeparatorModel
import com.heroes.model.ui_models.heroes_list.enums.HeroesListSeparatorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(private val heroesRepository: HeroesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = _uiEvent.asSharedFlow()

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
                    submitUiState(_uiState.value.copy(
                        searchState = _uiState.value.searchState.copy(focused = event.searchBarFocused)
                    ))
                }
                is UiEvent.ListIsScrolling -> {
                    val searchStateFocused = _uiState.value.searchState.focused
                    if (searchStateFocused.not() || event.listIsScrolling.not()) return@collect
                    submitUiState(_uiState.value.copy(
                        searchState = _uiState.value.searchState.copy(focused = false)
                    ))
                }
                UiEvent.ClearQueryClicked -> {
                    clearListToDefaultState()
                }
            }
        }
    }

    private fun clearListToDefaultState() = viewModelScope.launch {
        submitUiState(_uiState.value.copy(
            state = UiState.State.Data,
            heroesList = suggestedHeroes,
            searchState = _uiState.value.searchState.copy(query = "", searching = false)
        ))
    }

    private fun navigateToHeroDetails(heroModel: HeroModel) =
        submitAction(UiAction.NavigateToHeroesDetails(heroModel))

    private fun getHeroesByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        submitUiState(_uiState.value.copy(searchState = SearchState(name, _uiState.value.searchState.focused, true)))
        when (val response = heroesRepository.getHeroesByName(name)) {
            is NetworkResponse.Success -> {
                val heroesList = response.body
                if (heroesList.isEmpty()) {
                    clearListToDefaultState()
                    return@launch
                }
                val searchResultsWithSuggestedHeroes = suggestedHeroes.toMutableList().apply { addAll(heroesList) }
                submitUiState(
                    _uiState.value.copy(
                        state = UiState.State.Data,
                        heroesList = searchResultsWithSuggestedHeroes,
                        searchState = _uiState.value.searchState.copy(searching = false)
                    )
                )
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitUiState(UiState(errorMessage = message, state = UiState.State.Error))
            }
            else -> {}
        }
    }

    private fun getSuggestedHeroesList() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepository.getSuggestedHeroesList()) {
            is NetworkResponse.Success -> {
                val heroesList = response.body
                val heroesListWithSeparation = createHeroListWithSeparation(heroesList)
                suggestedHeroes.addAll(heroesListWithSeparation)
                submitUiState(
                    _uiState.value.copy(
                        state = UiState.State.Data,
                        heroesList = heroesListWithSeparation,
                        searchState = _uiState.value.searchState.copy(searching = false)
                    )
                )
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                submitUiState(
                    _uiState.value.copy(
                        state = UiState.State.Error,
                        searchState = _uiState.value.searchState.copy(searching = false),
                        errorMessage = message
                    )
                )
            }
            else -> {}
        }
    }

    private fun createHeroListWithSeparation(suggestedHeroesResult: List<HeroModel>): MutableList<BaseHeroModel> {
        val heroesListsModels = mutableListOf<BaseHeroModel>()
        heroesListsModels.add(0, HeroSeparatorModel(HeroesListSeparatorType.SUGGESTIONS))
        heroesListsModels.addAll(suggestedHeroesResult)
        heroesListsModels.add(HeroSeparatorModel(HeroesListSeparatorType.SEARCH))
        return heroesListsModels
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch {
        _uiAction.emit(uiAction)
    }

    private fun submitUiState(uiState: UiState)  {
        _uiState.update { uiState }
    }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(uiEvent)
    }

    sealed interface UiEvent {
        data class SearchQueryChanged(val searchText: String) : UiEvent
        data class SearchBarFocusChanged(val searchBarFocused: Boolean) : UiEvent
        data class ListIsScrolling(val listIsScrolling: Boolean) : UiEvent
        data class ListItemClicked(val heroModel: HeroModel) : UiEvent
        object ClearQueryClicked : UiEvent
    }

    data class UiState(
        val heroesList: List<BaseHeroModel> = emptyList(),
        val searchState : SearchState = SearchState("", focused = false, searching = true),
        val errorMessage: String = "",
        val state: State = State.Initial
    ) {
        enum class State {
            Data,
            Error,
            Initial
        }
    }

    sealed interface UiAction {
        data class NavigateToHeroesDetails(val heroModel: HeroModel) : UiAction
    }
}