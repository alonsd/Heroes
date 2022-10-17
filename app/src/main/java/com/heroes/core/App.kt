package com.heroes.core

import android.app.Application
import com.heroes.service_locator.*
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class App : Application() {

//    companion object {
//        lateinit var instance: App
//    }

    override fun onCreate() {
        super.onCreate()
//        instance = this
//        setupKoin()
    }

//    private fun setupKoin() {
//        startKoin {
//            androidContext(this@App)
//            modules(networkModule, repositoryModule, viewModelModule, remoteDataSourceModule)
//        }
//    }
}