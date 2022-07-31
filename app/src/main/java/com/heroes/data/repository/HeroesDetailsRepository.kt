package com.heroes.data.repository

import com.haroldadmin.cnradapter.NetworkResponse

interface HeroesDetailsRepository {

    suspend fun getHeroDetails(heroId: String): NetworkResponse<*, String>
}