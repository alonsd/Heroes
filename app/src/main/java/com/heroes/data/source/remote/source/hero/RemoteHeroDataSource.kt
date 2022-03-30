package com.heroes.data.source.remote.source.hero

import com.haroldadmin.cnradapter.NetworkResponse

interface RemoteHeroDataSource {

    suspend fun getHeroesByNameWithSuggestions(name: String): NetworkResponse<*, String>

    suspend fun getSuggestedHeroesList(addSeparation: Boolean): NetworkResponse<*, String>

}