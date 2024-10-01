package com.insact.timeapp

import android.app.Application
import com.insact.timeapp.di.appModule
import com.insact.timeapp.di.repositoryModule
import com.insact.timeapp.di.useCaseModule
import com.insact.timeapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}