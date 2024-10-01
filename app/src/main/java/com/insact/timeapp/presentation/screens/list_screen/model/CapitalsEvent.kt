package com.insact.timeapp.presentation.screens.list_screen.model

import androidx.annotation.StringRes

sealed interface CapitalsEvent {
    data class ShowError(@StringRes val resId: Int): CapitalsEvent
}