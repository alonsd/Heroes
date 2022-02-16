package com.bankhapoalimheroes.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bankhapoalimheroes.data.source.local.dao.BasicApplicationDao
import com.bankhapoalimheroes.database.converter.BasicApplicationConverter
import com.bankhapoalimheroes.model.entities.BasicApplicationEntity
import com.bankhapoalimheroes.utils.application.App
import com.bankhapoalimheroes.utils.constants.Database.BASIC_APPLICATION_DATABASE


@Database(entities = [BasicApplicationEntity::class], version = 1, exportSchema = false)
@TypeConverters(BasicApplicationConverter::class)
abstract class BasicApplicationDatabase : RoomDatabase() {


    abstract fun basicApplicationDao () : BasicApplicationDao

    companion object {

        private var instance : BasicApplicationDatabase? = null

        @Synchronized
        fun getInstance(): BasicApplicationDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(App.applicationContext(), BasicApplicationDatabase::class.java, BASIC_APPLICATION_DATABASE)
                    .fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }

}