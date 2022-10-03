package com.pavelhabzansky.sportsrec.features.record_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.features.record_detail.model.RecordDetail
import com.pavelhabzansky.sportsrec.features.record_detail.model.RecordDetailEvent

@Composable
fun RecordDetailScreen(
    navigateUp: () -> Unit,
    viewModel: RecordDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateUp -> navigateUp()
                is UiEvent.Snackbar -> {}
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.screen_title_detail)) },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigate back",
                        modifier = Modifier.clickable { navigateUp() }
                    )
                }
            )
        }
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            val record = viewModel.record
            if (record != null) {
                // Sport
                OutlinedTextField(
                    value = stringResource(id = record.type.textResource),
                    onValueChange = { },
                    enabled = false,
                    label = { Text(text = stringResource(id = R.string.record_detail_sport_label)) },
                )

                // Name
                OutlinedTextField(
                    value = record.name,
                    onValueChange = { },
                    enabled = false,
                    label = { Text(text = stringResource(id = R.string.record_detail_name_label)) },
                )


                when (record) {
                    is RecordDetail.Weightlifting -> WeightliftingRecordDetail(record = record)
                    is RecordDetail.Sprint -> SprintRecordDetail(record = record)
                    is RecordDetail.RopeJump -> RopJumpRecordDetail(record = record)
                    is RecordDetail.Custom -> CustomRecordDetail(record = record)
                }

                if (record.location != null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(record.location, 13f)
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(256.dp),
                        cameraPositionState = cameraPositionState,
                        uiSettings = MapUiSettings(scrollGesturesEnabled = false)
                    ) {
                        record.location.let {
                            Marker(
                                state = MarkerState(position = it),
                                title = record.name
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f, true))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onEvent(RecordDetailEvent.DeleteButtonClickedEvent) }
                ) {
                    Text(text = stringResource(id = R.string.record_detail_delete))
                }
            }
        }
    }
}

@Composable
fun WeightliftingRecordDetail(record: RecordDetail.Weightlifting) {
    Column {
        OutlinedTextField(
            value = record.weight,
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_weight_label)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = record.sets.toString(),
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_sets_label)) },
        )

        Spacer(modifier = Modifier.width(16.dp))

        repeat(record.sets) { i ->
            OutlinedTextField(
                value = record.sets.toString(),
                onValueChange = { },
                enabled = false,
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.record_detail_set_label,
                            i + 1
                        )
                    )
                },
            )
        }
    }
}

@Composable
fun SprintRecordDetail(record: RecordDetail.Sprint) {
    Column {
        OutlinedTextField(
            value = record.distance,
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_distance_label)) },
        )

        OutlinedTextField(
            value = record.time,
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_time_label)) },
        )
    }
}

@Composable
fun RopJumpRecordDetail(record: RecordDetail.RopeJump) {
    Column {
        OutlinedTextField(
            value = record.jumps,
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_jumps_label)) },
        )

        OutlinedTextField(
            value = record.time,
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_time_label)) },
        )
    }
}

@Composable
fun CustomRecordDetail(record: RecordDetail.Custom) {
    Column {
        OutlinedTextField(
            value = record.performance,
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_custom_label)) },
        )

        OutlinedTextField(
            value = record.time,
            onValueChange = { },
            enabled = false,
            label = { Text(text = stringResource(id = R.string.record_detail_time_label)) },
        )
    }
}