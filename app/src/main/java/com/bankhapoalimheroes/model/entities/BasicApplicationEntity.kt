package com.bankhapoalimheroes.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bankhapoalimheroes.utils.constants.Database.BASIC_APPLICATION_TABLE


@Entity(tableName = BASIC_APPLICATION_TABLE)
data class BasicApplicationEntity(@PrimaryKey val name : String)