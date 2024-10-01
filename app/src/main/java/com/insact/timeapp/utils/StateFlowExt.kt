package com.insact.timeapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.asStateWithLifecycle(): T {
    val state by collectAsStateWithLifecycle()
    return state
}