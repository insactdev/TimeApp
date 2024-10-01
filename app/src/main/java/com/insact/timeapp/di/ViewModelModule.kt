package com.insact.timeapp.di

import androidx.navigation.NavHostController
import com.insact.timeapp.presentation.screens.list_screen.CapitalsViewModel
import com.insact.timeapp.presentation.screens.main_screen.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (navigation: NavHostController) ->
        CapitalsViewModel(
            navigation = navigation,
            getCapitalsUseCase = get(),
            getSelectedCapitalUseCase = get(),
            setSelectedCapitalUseCase = get()
        )
    }

    viewModel { (navigation: NavHostController) ->
        MainScreenViewModel(
            navigation = navigation,
            getCapitalsUseCase = get(),
            getSelectedCapitalUseCase = get(),
            setSelectedCapitalUseCase = get(),
            getTimeUseCase = get()
        )
    }
}