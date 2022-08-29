package com.heroes.service_locator

import com.heroes.data.source.remote.api.HeroesApi
import com.heroes.data.source.remote.api.HeroesDetailsApi
import com.heroes.core.constants.NetworkConstants
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    single { provideHeroesApi(get()) }
    single { provideHeroesDetailsApi(get()) }
}

private fun provideRetrofit() =
    Retrofit.Builder()
        .baseUrl(NetworkConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .client(provideOkHttpClient())
        .build()

private fun provideHeroesApi(retrofit: Retrofit): HeroesApi = retrofit.create(HeroesApi::class.java)
private fun provideHeroesDetailsApi(retrofit: Retrofit): HeroesDetailsApi = retrofit.create(HeroesDetailsApi::class.java)

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }).build()
}