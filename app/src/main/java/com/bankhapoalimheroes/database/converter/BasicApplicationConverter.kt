package com.bankhapoalimheroes.database.converter

import androidx.room.TypeConverter
import com.bankhapoalimheroes.model.entities.BasicApplicationEntity

class BasicApplicationConverter {

    @TypeConverter
    fun basicApplicationModelToString(entity: BasicApplicationEntity): String {
        return entity.name
    }

    @TypeConverter
    fun stringToBasicApplicationModel(string: String): BasicApplicationEntity {
        return BasicApplicationEntity(string)
    }
}