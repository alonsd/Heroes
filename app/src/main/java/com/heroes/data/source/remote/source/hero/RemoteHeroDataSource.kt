package com.heroes.data.source.remote.source.hero

import com.haroldadmin.cnradapter.NetworkResponse

interface RemoteHeroDataSource {

    suspend fun getHeroesByName(name: String): NetworkResponse<*, String>

    suspend fun getSuggestedHeroesList(): NetworkResponse<*, String>

}