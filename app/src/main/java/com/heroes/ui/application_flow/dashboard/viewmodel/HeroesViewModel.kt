package com.heroes.ui.application_flow.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.heroes.data.repository.HeroesRepository
import com.heroes.model.ui_models.heroes_list.HeroesListModel
import com.haroldadmin.cnradapter.NetworkResponse
import com.heroes.model.ui_models.heroes_list.BaseHeroListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HeroesViewModel(private val heroesRepository: HeroesRepository) : ViewModel() {

    private val mutableDataFlow = MutableStateFlow<HeroesViewModelActions>(HeroesViewModelActions.GetSuggestedList)
    val actions = mutableDataFlow.asLiveData()

    fun getHeroesByName(name : String) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepository.getHeroesByNameWithSuggestions(name)) {
            is NetworkResponse.Success -> {
                mutableDataFlow.emit(HeroesViewModelActions.ShowHeroesList(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                response.error.message?.let { message ->
                    mutableDataFlow.emit(HeroesViewModelActions.ShowGeneralError(message))
                }
            }
            else -> {}
        }
    }

    fun getSuggestedHeroesList() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepository.getSuggestedHeroesList(true)) {
            is NetworkResponse.Success -> {
                mutableDataFlow.emit(HeroesViewModelActions.ShowHeroesList(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                response.error.message?.let { message ->
                    mutableDataFlow.emit(HeroesViewModelActions.ShowGeneralError(message))
                }
            }
            else -> {}
        }
    }

    sealed class HeroesViewModelActions {
        data class ShowHeroesList(val modelsListResponse: List<BaseHeroListModel>) : HeroesViewModelActions()
        data class ShowGeneralError(val errorMessage: String) : HeroesViewModelActions()
        object GetSuggestedList : HeroesViewModelActions()
    }
}