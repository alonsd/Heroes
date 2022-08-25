package com.heroes.ui.application_flow.hero_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.data.repository.HeroesDetailsRepositoryImpl
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HeroesDetailsViewModel(
    heroListModel : HeroesListModel,
    private val heroesDetailsRepositoryImpl: HeroesDetailsRepositoryImpl
) : ViewModel() {

    private val internalUiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = internalUiState.asStateFlow()

    private val internalUiAction = MutableSharedFlow<UiAction>()
    val uiAction = internalUiAction.asSharedFlow()

    private val mutableUiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = mutableUiEvent.asSharedFlow()

    init {
        getAdditionalHeroDetails(heroListModel.id)
        observeUiEvents()
    }

    private fun observeUiEvents() = viewModelScope.launch {
        uiEvent.collect { event ->
            when(event) {
                UiEvent.FloatingActionButtonClicked -> {
                    submitAction(UiAction.ShareHeroesDetails)
                }
            }
        }
    }



    private fun getAdditionalHeroDetails(heroId: String) = viewModelScope.launch {
        when (val response = heroesDetailsRepositoryImpl.getHeroDetails(heroId)) {
            is NetworkResponse.Success -> {
                val heroDetailsModel = response.body as HeroDetailsModel
                val showHeroPlaceOfBirth = heroDetailsModel.placeOfBirth.length >= 2
                internalUiState.emit(UiState.Data(heroDetailsModel, showHeroPlaceOfBirth))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                internalUiState.emit(UiState.Error(message))
            }
            else -> Unit
        }
    }

    private fun submitAction(uiAction: UiAction) = viewModelScope.launch { internalUiAction.emit(uiAction) }

    fun submitEvent(uiEvent: UiEvent) = viewModelScope.launch { mutableUiEvent.emit(uiEvent) }

    sealed class UiEvent {
        object FloatingActionButtonClicked : UiEvent()
    }

    sealed class UiState {
        data class Data(val heroDetailsModel: HeroDetailsModel, val showHeroPlaceOfBirth: Boolean) : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Initial : UiState()
    }

    sealed class UiAction {
        object ShareHeroesDetails : UiAction()
    }
}


