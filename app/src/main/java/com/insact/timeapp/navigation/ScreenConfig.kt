package com.insact.timeapp.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenConfig {
    @Serializable
    data object MainScreenRoute: ScreenConfig
    @Serializable
    data object CapitalsRoute: ScreenConfig
}