package com.pavelhabzansky.sportsrec.features.record_list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent

@Composable
fun RecordsListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    Column {
        Text(text = "This is records list screen")
    }
}