package com.insact.timeapp.presentation.screens.list_screen.model

sealed interface CapitalsIntent {
    data class SelectCapital(val capital: String): CapitalsIntent
}