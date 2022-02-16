package com.bankhapoalimheroes.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bankhapoalimheroes.data.repository.HeroesRepository
import com.bankhapoalimheroes.model.models.HeroesListResponseModel
import com.bankhapoalimheroes.model.ui_models.HeroesListModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HeroesViewModel(private val heroesRepository: HeroesRepository) : ViewModel() {

    private val mutableDataFlow = MutableStateFlow<MainViewModelActions>(MainViewModelActions.EmptyValue)
    val actions = mutableDataFlow.asLiveData()

    fun getHeroesByName(name : String) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = heroesRepository.getHeroesListByName(name)) {
            is NetworkResponse.Success -> {
                mutableDataFlow.emit(MainViewModelActions.HandleHeroesList(response.body as List<HeroesListModel>))
            }

            is NetworkResponse.Error -> {
                response.error.message?.let { message ->
                    mutableDataFlow.emit(MainViewModelActions.ShowGeneralError(message))
                }
            }
            else -> {}
        }
    }

    sealed class MainViewModelActions {
        data class HandleHeroesList(val modelsListResponse: List<HeroesListModel>) : MainViewModelActions()
        data class ShowGeneralError(val errorMessage: String) : MainViewModelActions()
        object EmptyValue : MainViewModelActions()
    }
}