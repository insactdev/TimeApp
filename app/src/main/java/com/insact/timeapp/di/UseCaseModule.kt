package com.insact.timeapp.di

import com.insact.timeapp.domain.usecase.GetCapitalsUseCase
import com.insact.timeapp.domain.usecase.GetSelectedCapitalUseCase
import com.insact.timeapp.domain.usecase.GetTimeUseCase
import com.insact.timeapp.domain.usecase.SetSelectedCapitalUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetCapitalsUseCase(
            repository = get()
        )
    }

    factory {
        GetSelectedCapitalUseCase(
            repository = get()
        )
    }

    factory {
        SetSelectedCapitalUseCase(
            repository = get()
        )
    }

    factory {
        GetTimeUseCase(
            repository = get()
        )
    }
}