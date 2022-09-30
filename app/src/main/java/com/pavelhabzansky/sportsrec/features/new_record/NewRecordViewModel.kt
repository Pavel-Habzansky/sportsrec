package com.pavelhabzansky.sportsrec.features.new_record

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.pavelhabzansky.domain.features.new_record.usecase.*
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.BaseViewModel
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.domain.core.Result
import com.pavelhabzansky.sportsrec.core.ui.UiText
import com.pavelhabzansky.sportsrec.core.utils.mapToString
import com.pavelhabzansky.sportsrec.features.new_record.NewRecordEvent.RecordTypeChanged
import com.pavelhabzansky.sportsrec.features.new_record.NewRecordEvent.StorageTypeChanged
import com.pavelhabzansky.sportsrec.features.new_record.model.NewRecordType
import com.pavelhabzansky.sportsrec.features.new_record.model.StorageTypeView
import com.pavelhabzansky.sportsrec.features.new_record.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NewRecordViewModel @Inject constructor(
    private val isWeightInputValid: IsWeightInputValidUseCase,
    private val isRepsInputValid: IsRepsInputValidUseCase,
    private val isSprintDistanceInputValid: IsSprintDistanceInputValidUseCase,
    private val isTimeInputValid: IsTimeInputValidUseCase,
    private val isRopeJumpsInputValid: IsRopeJumpsInputValidUseCase,
    private val isSportsRecordValid: IsSportsRecordValidUseCase,
    private val saveNewRecord: SaveNewRecordUseCase
) : BaseViewModel() {

    var newRecordState by mutableStateOf<NewRecordState>(NewRecordState.None)
        private set

    var name by mutableStateOf("")
        private set

    var storageType by mutableStateOf(StorageTypeView.LOCAL)
        private set

    var selectedLocation by mutableStateOf<LatLng?>(null)
        private set


    fun onEvent(event: NewRecordEvent) {
        when (event) {
            is RecordTypeChanged -> changeRecordType(event.newType)
            is StorageTypeChanged -> storageType = event.newStorageType
            is NewRecordEvent.OnLocationSelected -> selectedLocation = event.newLocation
            is WeightliftingRecordEvent -> handleWeightliftingRecordEvent(event)
            is SprintRecordEvent -> handleSprintRecordEvent(event)
            is RopeJumpRecordEvent -> handleRopeJumpRecordEvent(event)
            is CustomRecordEvent -> handleCustomRecordEvent(event)
            is NewRecordEvent.SaveButtonClicked -> handleSaveButtonClick()
        }
    }

    private fun handleSaveButtonClick() {
        val record = try {
            SportsRecord(
                name = name,
                performanceRecord = newRecordState.toDomain(),
                location = selectedLocation?.mapToString(),
                createTime = LocalDateTime.now(),
                storage = storageType.toDomain()
            )
        } catch (ex: Exception) {
            viewModelScope.launch(Dispatchers.Main) {
                _uiEvent.send(UiEvent.Snackbar(UiText.ResourceText(R.string.new_record_invalid_inputs)))
            }
            return
        }

        if (!isSportsRecordValid(IsSportsRecordValidUseCase.Params(record))) {
            viewModelScope.launch(Dispatchers.Main) {
                _uiEvent.send(UiEvent.Snackbar(UiText.ResourceText(R.string.new_record_invalid_inputs)))
            }
            return
        }

        val params = SaveNewRecordUseCase.Params(
            record = record
        )

        viewModelScope.launch {
            when (val result = saveNewRecord(params)) {
                is Result.Success -> {
                    Timber.i("New record successfully saved.")
                    _uiEvent.send(UiEvent.NavigateUp)
                }
                is Result.Failure -> {
                    Timber.w(result.throwable, "Couldn't save new record")
                }
            }
        }
    }

    private fun handleCustomRecordEvent(event: CustomRecordEvent) {
        when (event) {
            is CustomRecordEvent.OnNameInputEvent -> name = event.newName
            is CustomRecordEvent.OnPerformanceValueInputEvent -> {
                val currentState = newRecordState as NewRecordState.Custom
                newRecordState = currentState.copy(
                    performance = event.performance
                )
            }
            is CustomRecordEvent.OnCustomTimeInputEvent -> {
                val params = IsTimeInputValidUseCase.Params(event.newTime)
                if (!isTimeInputValid(params)) {
                    return
                }

                val currentState = newRecordState as NewRecordState.Custom
                newRecordState = currentState.copy(
                    time = event.newTime
                )
            }
        }
    }

    private fun handleRopeJumpRecordEvent(event: RopeJumpRecordEvent) {
        when (event) {
            is RopeJumpRecordEvent.OnNameInputEvent -> name = event.newName
            is RopeJumpRecordEvent.OnJumpsInputEvent -> {
                val params = IsRopeJumpsInputValidUseCase.Params(event.newJumpCount)
                if (!isRopeJumpsInputValid(params)) {
                    return
                }

                val currentState = newRecordState as NewRecordState.RopeJump
                newRecordState = currentState.copy(
                    jumps = event.newJumpCount
                )
            }
            is RopeJumpRecordEvent.OnRopeJumpTimeInputEvent -> {
                val params = IsTimeInputValidUseCase.Params(event.newTime)
                if (!isTimeInputValid(params)) {
                    return
                }

                val currentState = newRecordState as NewRecordState.RopeJump
                newRecordState = currentState.copy(
                    time = event.newTime
                )
            }
        }
    }

    private fun handleSprintRecordEvent(event: SprintRecordEvent) {
        when (event) {
            is SprintRecordEvent.OnNameInputEvent -> name = event.newName
            is SprintRecordEvent.OnSprintDistanceInputEvent -> {
                val params = IsSprintDistanceInputValidUseCase.Params(event.newDistance)
                if (!isSprintDistanceInputValid(params)) {
                    return
                }

                val currentState = newRecordState as NewRecordState.Sprint
                newRecordState = currentState.copy(
                    distanceMeters = event.newDistance
                )
            }
            is SprintRecordEvent.OnSprintTimeInputEvent -> {
                val params = IsTimeInputValidUseCase.Params(event.newTime)
                if (!isTimeInputValid(params)) {
                    return
                }

                val currentState = newRecordState as NewRecordState.Sprint
                newRecordState = currentState.copy(
                    time = event.newTime
                )
            }
        }
    }

    private fun handleWeightliftingRecordEvent(event: WeightliftingRecordEvent) {
        when (event) {
            is WeightliftingRecordEvent.OnNameInputEvent -> name = event.newName
            is WeightliftingRecordEvent.OnWeightInputEvent -> {
                val params = IsWeightInputValidUseCase.Params(event.newWeight)
                if (!isWeightInputValid(params)) {
                    return
                }
                // I tried to avoid type checking/type casting newRecordState variable but there might be a better way to handle this.
                // This could possibly be thread unsafe
                val currentState = newRecordState as NewRecordState.Weightlifting
                newRecordState = currentState.copy(
                    weight = event.newWeight
                )
            }
            is WeightliftingRecordEvent.OnSetsInputEvent -> {
                val currentState = newRecordState as NewRecordState.Weightlifting
                val currentRepMap = currentState.repsPerSet
                val newRepMap = mutableMapOf<Int, String>()
                repeat(event.newSetCount) { i -> newRepMap[i] = currentRepMap[i] ?: "" }

                newRecordState = currentState.copy(
                    sets = event.newSetCount,
                    repsPerSet = newRepMap
                )
            }
            is WeightliftingRecordEvent.OnRepsInputEvent -> {
                val params = IsRepsInputValidUseCase.Params(event.newRepCount)
                if (!isRepsInputValid(params)) {
                    return
                }

                val currentState = newRecordState as NewRecordState.Weightlifting
                val newRepsPerSet = currentState.repsPerSet.toMutableMap()
                newRepsPerSet[event.set] = event.newRepCount
                newRecordState = currentState.copy(
                    repsPerSet = newRepsPerSet
                )
            }
        }
    }

    private fun changeRecordType(newType: NewRecordType) {
        if (newType == newRecordState.type) return

        Timber.i("Record type is being changed to: ${newType.name}")
        when (newType) {
            NewRecordType.WEIGHTLIFTING -> {
                val repsPerSet = mutableMapOf<Int, String>()
                repeat(DEFAULT_SET_COUNT) { i -> repsPerSet[i] = "" }

                newRecordState = NewRecordState.Weightlifting(
                    sets = repsPerSet.size,
                    repsPerSet = repsPerSet
                )
            }
            NewRecordType.SPRINT -> newRecordState = NewRecordState.Sprint()
            NewRecordType.ROPE_JUMP -> newRecordState = NewRecordState.RopeJump()
            NewRecordType.CUSTOM -> newRecordState = NewRecordState.Custom()
            else -> {}
        }
    }

    companion object {
        private const val DEFAULT_SET_COUNT = 3
    }
}