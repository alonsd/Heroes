package com.bankhapoalimheroes.utils.application

import android.app.Application
import android.content.Context
import com.bankhapoalimheroes.service_locator.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(networkModule, repositoryModule, viewModelModule, remoteDataSourceModule)
        }
    }
}