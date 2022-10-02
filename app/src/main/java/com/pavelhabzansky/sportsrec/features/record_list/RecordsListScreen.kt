package com.pavelhabzansky.sportsrec.features.record_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.components.LoadingDialog
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.features.record_list.model.RecordListItem

@Composable
fun RecordsListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: RecordsListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)
    DisposableEffect(key1 = lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    viewModel.onResume()
                }
                else -> {}
            }

        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.Snackbar -> snackbarHostState.showSnackbar(
                    message = event.text.asText(context)
                )
                else -> {}
            }
        }
    }

    when (viewModel.screenState) {
        is RecordsListScreenState.Filter -> {
            FilterDialog(
                options = viewModel.filterOptions,
                onDismissRequest = { viewModel.onEvent(RecordsListEvent.FilterDismissed) },
                onSave = { local, remote ->
                    viewModel.onEvent(
                        RecordsListEvent.FilterOptionsSaved(
                            local = local,
                            remote = remote
                        )
                    )
                }
            )
        }
        is RecordsListScreenState.Uploading -> {
            LoadingDialog(text = stringResource(id = R.string.loading_upload))
        }
        is RecordsListScreenState.Synchronizing -> {
            LoadingDialog(text = stringResource(id = R.string.loading_synchronize))
        }
        else -> {}
    }

    val records by viewModel.sportsRecords.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.padding(8.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(RecordsListEvent.NewRecordClick) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ControlBar(clickHandler = viewModel::onEvent)
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            if (records.isEmpty()) {
                Empty()
            } else {
                LazyColumn {
                    items(records) { record ->
                        SportRecordItem(record)
                    }
                }
            }
        }
    }
}

@Composable
fun SportRecordItem(item: RecordListItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { },
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(item.color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.name, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))

            PerformanceContent(item)

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = item.createTime)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(item.storageType.color)
        )
    }
}

@Composable
fun PerformanceContent(item: RecordListItem) {
    Column {
        when (item) {
            is RecordListItem.WeightliftingListItem -> {
                Text(text = stringResource(id = R.string.list_item_sets, item.sets))
                Text(text = stringResource(id = R.string.list_item_weight, item.weight))
                Text(text = stringResource(id = R.string.list_item_total_work, item.totalWeight))
            }
            is RecordListItem.SprintListItem -> {
                Text(text = stringResource(id = R.string.list_item_distance, item.distance))
                Text(text = stringResource(id = R.string.list_item_time, item.time))
            }
            is RecordListItem.RopeJumpListItem -> {
                Text(text = stringResource(id = R.string.list_item_jumps, item.jumps))
                Text(text = stringResource(id = R.string.list_item_time, item.time))
            }
            is RecordListItem.CustomListItem -> {
                Text(text = stringResource(id = R.string.list_item_performance, item.performance))
                Text(text = stringResource(id = R.string.list_item_time, item.time))
            }
        }
    }
}

@Composable
fun Empty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.records_list_empty_message))
    }
}