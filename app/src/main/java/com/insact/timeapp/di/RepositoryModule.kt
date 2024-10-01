package com.insact.timeapp.di

import com.insact.timeapp.data.repository.RepositoryImpl
import com.insact.timeapp.domain.Repository
import org.koin.dsl.module

val repositoryModule = module {
    factory<Repository> {
        RepositoryImpl(
            api = get(),
            sharedPrefs = get()
        )
    }
}