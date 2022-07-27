package com.heroes.ui.application_flow.hero_details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HeroesDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val heroesDetailsRepository: HeroesDetailsRepository
) : ViewModel() {

    private val internalUiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = internalUiState.asStateFlow()

    private val internalUiAction = MutableSharedFlow<UiAction>()
    val uiAction = internalUiAction.asSharedFlow()

    private val mutableUiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvent = mutableUiEvent.asSharedFlow()

    init {
        val heroesListModel = savedStateHandle.get<HeroesListModel>("heroModel") ?: throw IllegalArgumentException("Passed model can't be null")
        getAdditionalHeroDetails(heroesListModel.id)
    }

    private fun getAdditionalHeroDetails(heroId: String) = viewModelScope.launch {
        when (val response = heroesDetailsRepository.getHeroDetails(heroId)) {
            is NetworkResponse.Success -> {
                internalUiState.emit(UiState.Data(response.body as HeroDetailsModel))
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: return@launch
                internalUiState.emit(UiState.Error(message))
            }
            else -> Unit
        }
    }


    sealed class UiEvent {

    }

    sealed class UiState {
        data class Data(val heroDetailsModel: HeroDetailsModel) : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Initial : UiState()
    }

    sealed class UiAction {

    }
}


