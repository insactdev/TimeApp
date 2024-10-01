package com.insact.timeapp.presentation.screens.list_screen.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CapitalItem(
    capital: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect.invoke()
            }
    ) {
        Text(
            text = capital,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
        )
        RadioButton(
            selected = selected,
            onClick = null
        )
    }
}
