package com.bankhapoalimheroes.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bankhapoalimheroes.data.repository.HeroesDetailsRepository
import com.bankhapoalimheroes.model.ui_models.hero_details.HeroDetailsModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HeroesDetailsViewModel(private val heroesDetailsRepository: HeroesDetailsRepository) : ViewModel() {

    private val mutableDataFlow = MutableStateFlow<HeroDetailsViewModelActions>(HeroDetailsViewModelActions.EmptyValue)
    val actions = mutableDataFlow.asLiveData()

    fun getHeroDetails(heroId : String) = viewModelScope.launch {
        when(val response = heroesDetailsRepository.getHeroDetails(heroId)) {
            is NetworkResponse.Success -> {
                mutableDataFlow.emit(HeroDetailsViewModelActions.ShowHeroDetails(response.body as HeroDetailsModel))
            }

            is NetworkResponse.Error -> {
                response.error.message?.let { message ->
                    mutableDataFlow.emit(HeroDetailsViewModelActions.ShowGeneralError(message))
                }
            }
            else -> {}
        }
    }


    sealed class HeroDetailsViewModelActions {
        data class ShowHeroDetails(val heroDetailsModel: HeroDetailsModel) : HeroDetailsViewModelActions()
        data class ShowGeneralError(val errorMessage: String) : HeroDetailsViewModelActions()
        object EmptyValue : HeroDetailsViewModelActions()
    }
}


