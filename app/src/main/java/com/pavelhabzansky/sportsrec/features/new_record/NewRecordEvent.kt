package com.pavelhabzansky.sportsrec.features.new_record

import com.google.android.gms.maps.model.LatLng
import com.pavelhabzansky.sportsrec.features.new_record.model.RecordType
import com.pavelhabzansky.sportsrec.features.new_record.model.StorageTypeView

sealed class NewRecordEvent {
    data class RecordTypeChanged(val newType: RecordType) : NewRecordEvent()
    data class StorageTypeChanged(val newStorageType: StorageTypeView) : NewRecordEvent()
    data class OnLocationSelected(val newLocation: LatLng) : NewRecordEvent()
    object SaveButtonClicked : NewRecordEvent()
}

sealed class WeightliftingRecordEvent : NewRecordEvent() {
    data class OnNameInputEvent(val newName: String) : WeightliftingRecordEvent()

    data class OnWeightInputEvent(val newWeight: String) : WeightliftingRecordEvent()
    data class OnSetsInputEvent(val newSetCount: Int) : WeightliftingRecordEvent()
    data class OnRepsInputEvent(val set: Int, val newRepCount: String) : WeightliftingRecordEvent()
}

sealed class SprintRecordEvent : NewRecordEvent() {
    data class OnNameInputEvent(val newName: String) : SprintRecordEvent()

    data class OnSprintDistanceInputEvent(val newDistance: String) : SprintRecordEvent()
    data class OnSprintTimeInputEvent(val newTime: String) : SprintRecordEvent()
}

sealed class RopeJumpRecordEvent : NewRecordEvent() {
    data class OnNameInputEvent(val newName: String) : RopeJumpRecordEvent()

    data class OnJumpsInputEvent(val newJumpCount: String) : RopeJumpRecordEvent()
    data class OnRopeJumpTimeInputEvent(val newTime: String) : RopeJumpRecordEvent()
}

sealed class CustomRecordEvent : NewRecordEvent() {
    data class OnNameInputEvent(val newName: String) : CustomRecordEvent()

    data class OnPerformanceValueInputEvent(val performance: String) : CustomRecordEvent()
    data class OnCustomTimeInputEvent(val newTime: String) : CustomRecordEvent()
}