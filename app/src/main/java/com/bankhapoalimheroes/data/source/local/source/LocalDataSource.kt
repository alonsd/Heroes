package com.bankhapoalimheroes.data.source.local.source

import com.bankhapoalimheroes.data.source.local.dao.BasicApplicationDao


class LocalDataSource(private val basicApplicationDao: BasicApplicationDao) {

    suspend fun getBasicApplicationModel() =  basicApplicationDao.getBasicApplicationModel()

}