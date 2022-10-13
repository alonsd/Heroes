package com.heroes.ui.application_flow.hero_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.heroes.model.ui_models.heroes_list.HeroModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HeroesDetailsViewModel(
    heroListModel: HeroModel,
    private val heroesDetailsRepository: HeroesDetailsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = _uiEvent.asSharedFlow()

    init {
        getAdditionalHeroDetails(heroListModel)
        observeUiEvents()
    }

    private fun observeUiEvents() = viewModelScope.launch {
        uiEvent.collect { event ->
            when (event) {
                UiEvent.FloatingActionButtonClicked -> {
                    submitAction(UiAction.ShareHeroesDetails)
                }
            }
        }
    }


    private fun getAdditionalHeroDetails(hero: HeroModel) = viewModelScope.launch {
        when (val response = heroesDetailsRepository.getHeroDetails(hero.id)) {
            is NetworkResponse.Success -> {
                val heroDetailsModel = response.body
                val showHeroPlaceOfBirth = heroDetailsModel.placeOfBirth.length >= 2
                val uiState = _uiState.value.copy(
                    heroDetailsModel = heroDetailsModel,
                    showHeroPlaceOfBirth = showHeroPlaceOfBirth, state = UiState.State.Data,
                    heroImage = hero.image,
                    heroName = hero.name
                )
                _uiState.emit(uiState)
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                val uiState = _uiState.value.copy(
                    errorMessage = message,
                    state = UiState.State.Data
                )
                _uiState.emit(uiState)
            }
            else -> Unit
        }
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch { _uiAction.emit(uiAction) }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch { _uiEvent.emit(uiEvent) }

    sealed interface UiEvent {
        object FloatingActionButtonClicked : UiEvent
    }

    data class UiState(
        val heroDetailsModel: HeroDetailsModel = HeroDetailsModel("", emptyList()),
        val showHeroPlaceOfBirth: Boolean = true,
        val heroImage : String = "",
        val heroName : String = "",
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
        object ShareHeroesDetails : UiAction
    }
}


