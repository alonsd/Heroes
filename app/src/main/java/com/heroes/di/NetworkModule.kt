package com.heroes.di

import android.app.Application
import android.content.Context
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.heroes.core.constants.NetworkConstants
import com.heroes.data.source.remote.api.HeroesApi
import com.heroes.data.source.remote.api.HeroesDetailsApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideHeroesApi(retrofit: Retrofit): HeroesApi {
        return retrofit.create(HeroesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeroesDetailsApi(retrofit: Retrofit): HeroesDetailsApi {
        return retrofit.create(HeroesDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(provideOkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()
    }
}