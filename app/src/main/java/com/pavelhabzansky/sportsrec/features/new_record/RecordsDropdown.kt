package com.pavelhabzansky.sportsrec.features.new_record

import androidx.compose.material.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecordsDropdown(
    selectedValue: String,
    onDismissRequest: () -> Unit,
    expanded: Boolean,
    labelText: String,
    onExpandedChange: (Boolean) -> Unit,
    menuItems: @Composable () -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            onExpandedChange(it)
        }
    ) {

        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = labelText) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onDismissRequest()
            }
        ) {

            menuItems()
        }
    }
}