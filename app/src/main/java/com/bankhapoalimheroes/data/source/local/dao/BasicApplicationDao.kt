package com.bankhapoalimheroes.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.bankhapoalimheroes.model.entities.BasicApplicationEntity


@Dao
interface BasicApplicationDao {

    @Query("select * from BasicApplicationTable")
    suspend fun getBasicApplicationModel() : BasicApplicationEntity
}