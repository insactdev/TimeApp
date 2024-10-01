package com.insact.timeapp.presentation.screens.main_screen.model

sealed interface MainIntent {
    data object GoToSelectCapital: MainIntent
}