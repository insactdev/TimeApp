package com.insact.timeapp.di

import com.insact.timeapp.BuildConfig
import com.insact.timeapp.data.SharedPrefs
import com.insact.timeapp.data.api.TimeServiceApi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideRetrofit() }
    factory { SharedPrefs(androidContext()) }
}

private fun provideRetrofit() =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TimeServiceApi::class.java)


private fun createOkHttpClient() =
    OkHttpClient.Builder()
        .build()