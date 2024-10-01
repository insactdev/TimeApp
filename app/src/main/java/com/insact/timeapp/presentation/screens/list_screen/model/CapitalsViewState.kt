package com.insact.timeapp.presentation.screens.list_screen.model

import androidx.compose.runtime.Stable
import com.insact.timeapp.utils.EMPTY_STRING

@Stable
data class CapitalsViewState(
    val capitals: List<String> = emptyList(),
    val selectedCapital: String = EMPTY_STRING,
    val loading: Boolean = false
)

fun CapitalsViewState.setLoading(loading: Boolean) =
    copy(
        loading = loading
    )