package com.pavelhabzansky.sportsrec.features.record_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun RecordsListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: RecordsListViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> {}
            }
        }
    }

    val records by viewModel.sportsRecords.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(RecordsListEvent.NewRecordClick) }) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
            }
        }
    ) {
        if (records.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.records_list_empty_message))
            }
        } else {
            Column {
                Text(text = "Records are not empty")
            }
        }
    }

}