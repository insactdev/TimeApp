package com.insact.timeapp.presentation.screens.main_screen.model

sealed interface MainEvent {
    data class ShowError(val error: String): MainEvent
}