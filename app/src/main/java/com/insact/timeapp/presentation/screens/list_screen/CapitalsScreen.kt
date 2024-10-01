package com.insact.timeapp.presentation.screens.list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.insact.timeapp.R
import com.insact.timeapp.presentation.screens.list_screen.content.CapitalItem
import com.insact.timeapp.presentation.screens.list_screen.model.CapitalsEvent
import com.insact.timeapp.presentation.screens.list_screen.model.CapitalsIntent
import com.insact.timeapp.presentation.screens.list_screen.model.CapitalsViewState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CapitalsScreen(
    state: CapitalsViewState,
    event: SharedFlow<CapitalsEvent>,
    onIntent: (CapitalsIntent) -> Unit
) {

    var showAlert by remember { mutableStateOf(false) }
    var alertTitle by remember { mutableIntStateOf(R.string.empty_string) }

    LaunchedEffect(key1 = Unit) {
        event.collectLatest {
            when (it) {
                is CapitalsEvent.ShowError -> {
                    alertTitle = it.resId
                    showAlert = true
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(state.capitals) {
            CapitalItem(
                capital = it,
                selected = it == state.selectedCapital,
                onSelect = {
                    onIntent.invoke(
                        CapitalsIntent.SelectCapital(capital = it)
                    )
                }
            )
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
            )
        }
    }

    if (showAlert) {
        AlertDialog(
            title = {
                Text(text = stringResource(alertTitle))
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