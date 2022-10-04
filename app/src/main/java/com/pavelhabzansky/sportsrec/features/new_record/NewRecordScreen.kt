package com.pavelhabzansky.sportsrec.features.new_record

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.features.new_record.model.RecordType
import com.pavelhabzansky.sportsrec.features.new_record.model.StorageTypeView
import timber.log.Timber

val permissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

@Composable
fun NewRecordScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    navigateUp: () -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: NewRecordViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.reduce { acc, b -> acc && b }) {
            val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            locationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    viewModel.onEvent(
                        NewRecordEvent.OnLocationSelected(
                            LatLng(
                                task.result.latitude,
                                task.result.longitude
                            )
                        )
                    )
                }
            }
        }
    }

    SideEffect {
        checkLocationPermission(
            launcher = launcher,
            context = context,
            onLocationObtained = {
                if (viewModel.selectedLocation == null) {
                    viewModel.onEvent(NewRecordEvent.OnLocationSelected(it))
                }
            }
        )
    }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.NavigateUp -> navigateUp()
                is UiEvent.Snackbar -> snackbarHostState.showSnackbar(
                    message = event.text.asText(context)
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.screen_title_new_record)) },
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
            val newRecordState = viewModel.newRecordState
            var typesExpanded by remember { mutableStateOf(false) }
            RecordsDropdown(
                selectedValue = stringResource(id = newRecordState.type.textResource),
                expanded = typesExpanded,
                labelText = stringResource(id = R.string.new_record_sport_pick_label),
                onDismissRequest = {
                    focusManager.clearFocus()
                },
                onExpandedChange = {
                    typesExpanded = !typesExpanded
                },
            ) {
                RecordType.values().forEach {
                    if (it == RecordType.NONE) return@forEach

                    DropdownMenuItem(
                        onClick = {
                            typesExpanded = false
                            focusManager.clearFocus()

                            viewModel.onEvent(NewRecordEvent.RecordTypeChanged(it))
                        }
                    ) {
                        Text(text = stringResource(id = it.textResource))
                    }
                }
            }

            if (newRecordState !is NewRecord.None) {
                when (newRecordState) {
                    is NewRecord.Weightlifting -> WeightliftingInputForm(
                        viewModel = viewModel,
                        newRecordState = newRecordState
                    )
                    is NewRecord.Sprint -> SprintInputForm(
                        viewModel = viewModel,
                        newRecordState = newRecordState
                    )
                    is NewRecord.RopeJump -> RopeJumpInputForm(
                        viewModel = viewModel,
                        newRecordState = newRecordState
                    )
                    is NewRecord.Custom -> CustomInputForm(
                        viewModel = viewModel,
                        newRecordState = newRecordState
                    )
                    else -> {}
                }

                Spacer(modifier = Modifier.height(32.dp))

                // An interesting problem - we can't vertically scroll GoogleMap on vertically scrollable background
                // Map scroll needs to be handled manually - https://stackoverflow.com/questions/67542850/column-with-scrollable-modifier-breaks-map-vertical-scroll
                val cameraPositionState = rememberCameraPositionState {
                    viewModel.selectedLocation?.let {
                        position = CameraPosition.fromLatLngZoom(it, 15f)
                    }
                }
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                cameraPositionState.move(
                                    CameraUpdateFactory.scrollBy(
                                        dragAmount.x * -1,
                                        dragAmount.y * -1
                                    )
                                )
                            }
                        },
                    cameraPositionState = cameraPositionState,
                    onMapClick = { viewModel.onEvent(NewRecordEvent.OnLocationSelected(it)) }
                ) {
                    viewModel.selectedLocation?.let {
                        Marker(
                            state = MarkerState(position = it),
                            title = viewModel.name
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = stringResource(id = R.string.new_record_storage_type_title))
                StorageTypeView.values().forEach { storage ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = viewModel.storageType == storage,
                            onClick = { viewModel.onEvent(NewRecordEvent.StorageTypeChanged(storage)) }
                        )
                        Text(text = stringResource(id = storage.textId))
                    }
                }

                Spacer(modifier = Modifier.weight(1f, true))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onEvent(NewRecordEvent.SaveButtonClicked) }
                ) {
                    Text(text = stringResource(id = R.string.new_record_save))
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.new_record_sport_pick_message),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}

@Composable
fun SprintInputForm(
    viewModel: NewRecordViewModel,
    newRecordState: NewRecord.Sprint
) {
    Column {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onEvent(SprintRecordEvent.OnNameInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_name)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = newRecordState.distanceMeters,
            onValueChange = { viewModel.onEvent(SprintRecordEvent.OnSprintDistanceInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_sprint_distance)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            trailingIcon = { Text(text = stringResource(id = R.string.new_record_unit_distance)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newRecordState.time,
            onValueChange = { viewModel.onEvent(SprintRecordEvent.OnSprintTimeInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_time)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            trailingIcon = { Text(text = stringResource(id = R.string.new_record_unit_time)) }
        )
    }
}

@Composable
fun WeightliftingInputForm(
    viewModel: NewRecordViewModel,
    newRecordState: NewRecord.Weightlifting
) {
    val focusManager = LocalFocusManager.current
    Column {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onEvent(WeightliftingRecordEvent.OnNameInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_name)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = newRecordState.weight,
            onValueChange = {
                viewModel.onEvent(WeightliftingRecordEvent.OnWeightInputEvent(it))
            },
            label = { Text(text = stringResource(id = R.string.new_record_weight_label)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            trailingIcon = { Text(text = stringResource(id = R.string.new_record_unit_weight)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        var setCounterExpanded by remember { mutableStateOf(false) }
        RecordsDropdown(
            selectedValue = newRecordState.sets.toString(),
            expanded = setCounterExpanded,
            labelText = stringResource(id = R.string.new_record_set_pick_label),
            onDismissRequest = {
                focusManager.clearFocus()
            },
            onExpandedChange = {
                setCounterExpanded = !setCounterExpanded
            },
        ) {
            for (i in 1..10) {
                DropdownMenuItem(
                    onClick = {
                        setCounterExpanded = false
                        focusManager.clearFocus()

                        viewModel.onEvent(WeightliftingRecordEvent.OnSetsInputEvent(i))
                    }
                ) {
                    Text(text = i.toString())
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        repeat(newRecordState.sets) { i ->
            OutlinedTextField(
                value = newRecordState.repsPerSet[i].toString(),
                onValueChange = {
                    viewModel.onEvent(WeightliftingRecordEvent.OnRepsInputEvent(i, it))
                },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.new_record_set_input_label,
                            i + 1
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Composable
fun RopeJumpInputForm(
    viewModel: NewRecordViewModel,
    newRecordState: NewRecord.RopeJump
) {
    Column {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onEvent(RopeJumpRecordEvent.OnNameInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_name)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        OutlinedTextField(
            value = newRecordState.jumps,
            onValueChange = { viewModel.onEvent(RopeJumpRecordEvent.OnJumpsInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_rope_jumps)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newRecordState.time,
            onValueChange = { viewModel.onEvent(RopeJumpRecordEvent.OnRopeJumpTimeInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_time)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            trailingIcon = { Text(text = stringResource(id = R.string.new_record_unit_time)) }
        )
    }
}

@Composable
fun CustomInputForm(
    viewModel: NewRecordViewModel,
    newRecordState: NewRecord.Custom
) {
    Column {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onEvent(CustomRecordEvent.OnNameInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_name)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newRecordState.performance,
            onValueChange = { viewModel.onEvent(CustomRecordEvent.OnPerformanceValueInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_custom_performance)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newRecordState.time,
            onValueChange = { viewModel.onEvent(CustomRecordEvent.OnCustomTimeInputEvent(it)) },
            label = { Text(text = stringResource(id = R.string.new_record_time)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            trailingIcon = { Text(text = stringResource(id = R.string.new_record_unit_time)) }
        )
    }
}

@Preview
@Composable
fun NewRecordScreenPreview() {
    NewRecordScreen(
        onNavigate = {},
        navigateUp = {},
        snackbarHostState = SnackbarHostState()
    )
}

fun checkLocationPermission(
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    context: Context,
    onLocationObtained: (LatLng) -> Unit
) {
    if (permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }) {
        val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationProviderClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    onLocationObtained(latLng)
                } else {
                    Timber.w("Location couldn't be obtained")
                }
            }
        }
    } else {
        launcher.launch(permissions)
    }
}