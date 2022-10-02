package com.pavelhabzansky.sportsrec.features.record_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pavelhabzansky.domain.features.sports_records.model.FilterOptions
import com.pavelhabzansky.sportsrec.R

@Composable
fun FilterDialog(
    options: FilterOptions,
    onDismissRequest: () -> Unit,
    onSave: (local: Boolean, remote: Boolean) -> Unit
) {

    var localOptionCheckbox by remember { mutableStateOf(options.local) }
    var remoteOptionCheckbox by remember { mutableStateOf(options.remote) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(id = R.string.filter_dialog_title)) },
        text = {
            Column {
                Spacer(modifier = Modifier.height(16.dp))

                FilterCheckbox(
                    text = stringResource(id = R.string.filter_dialog_local),
                    localOptionCheckbox
                ) {
                    localOptionCheckbox = !localOptionCheckbox
                }
                FilterCheckbox(
                    text = stringResource(id = R.string.filter_dialog_remote),
                    remoteOptionCheckbox
                ) {
                    remoteOptionCheckbox = !remoteOptionCheckbox
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(localOptionCheckbox, remoteOptionCheckbox)
                onDismissRequest()
            }) {
                Text(text = stringResource(id = R.string.filter_dialog_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.filter_dialog_cancel))
            }
        }
    )
}

@Composable
fun FilterCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = text)
    }
}