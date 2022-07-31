package com.heroes.data.repository

interface HeroesRepository {

    suspend fun getHeroesByNameWithSuggestions(name: String)

    suspend fun getSuggestedHeroesList(addSeparation: Boolean)
}