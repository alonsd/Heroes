package com.heroes.ui.application_flow.hero_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.heroes.data.repository.HeroesDetailsRepository
import com.heroes.model.ui_models.hero_details.HeroDetailsModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HeroesDetailsViewModel(private val heroesDetailsRepository: HeroesDetailsRepository) : ViewModel() {

    private val mutableDataFlow = MutableStateFlow<HeroDetailsViewModelActions>(HeroDetailsViewModelActions.EmptyValue)
    val actions = mutableDataFlow.asLiveData()

    fun getAdditionalHeroDetails(heroId : String) = viewModelScope.launch {
        when(val response = heroesDetailsRepository.getHeroDetails(heroId)) {
            is NetworkResponse.Success -> {
                mutableDataFlow.emit(HeroDetailsViewModelActions.ShowAdditionalHeroDetails(response.body as HeroDetailsModel))
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
        data class ShowAdditionalHeroDetails(val heroDetailsModel: HeroDetailsModel) : HeroDetailsViewModelActions()
        data class ShowGeneralError(val errorMessage: String) : HeroDetailsViewModelActions()
        object EmptyValue : HeroDetailsViewModelActions()
    }
}


