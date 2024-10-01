package com.insact.timeapp.presentation.screens.main_screen.model

import androidx.compose.runtime.Stable
import com.insact.timeapp.utils.EMPTY_STRING

data class MainState(
    @Stable
    val capital: String = EMPTY_STRING,
    val timeString: String = EMPTY_STRING,
    @Stable
    val loading: Boolean = false
)

fun MainState.setLoading(loading: Boolean) =
    copy(loading = loading)
