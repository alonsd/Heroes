package com.heroes.data.repository

import com.haroldadmin.cnradapter.NetworkResponse

interface HeroesRepository {

    suspend fun getHeroesByNameWithSuggestions(name: String) : NetworkResponse<*, String>

    suspend fun getSuggestedHeroesList() : NetworkResponse<*, String>
}