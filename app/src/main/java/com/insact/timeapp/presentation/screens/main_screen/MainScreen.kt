package com.insact.timeapp.presentation.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.insact.timeapp.R
import com.insact.timeapp.presentation.screens.main_screen.model.MainEvent
import com.insact.timeapp.presentation.screens.main_screen.model.MainIntent
import com.insact.timeapp.presentation.screens.main_screen.model.MainState
import com.insact.timeapp.utils.EMPTY_STRING
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    state: MainState,
    event: SharedFlow<MainEvent>,
    onIntent: (MainIntent) -> Unit
) {

    var showAlert by remember { mutableStateOf(false) }
    var alertTitle by remember { mutableStateOf(EMPTY_STRING) }

    LaunchedEffect(key1 = Unit) {
        event.collectLatest {
            when (it) {
                is MainEvent.ShowError -> {
                    alertTitle = it.error
                    showAlert = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Text(
                text = state.capital,
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = state.timeString,
                style = MaterialTheme.typography.headlineMedium,
            )

            OutlinedButton(
                onClick = {
                    onIntent.invoke(
                        MainIntent.GoToSelectCapital
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.choose_capital)
                )
            }
        }
    }

    if (state.loading) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
            )
        }
    }

    if (showAlert) {
        AlertDialog(
            title = {
                Text(text = alertTitle)
            },
            onDismissRequest = {
                showAlert = false
            },
            confirmButton = {
                OutlinedButton(
                    onClick = { showAlert = false }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }
}
