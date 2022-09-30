package com.pavelhabzansky.sportsrec.features.new_record

import com.pavelhabzansky.domain.features.sports_records.model.PerformanceRecord
import com.pavelhabzansky.sportsrec.features.new_record.model.NewRecordType

sealed class NewRecordState(
    val type: NewRecordType
) {
    // I wish we could have copy() method on ordinary class objects, data class can have only val/var parameters to override
    // That way `name` could be part of NewRecordState properties just like `type` is
//    abstract val name: String

    object None : NewRecordState(type = NewRecordType.NONE)

    data class Weightlifting(
        val weight: String = "",
        val sets: Int,
        val repsPerSet: Map<Int, String>
    ) : NewRecordState(type = NewRecordType.WEIGHTLIFTING)

    data class Sprint(
        val distanceMeters: String = "",
        val time: String = ""
    ) : NewRecordState(type = NewRecordType.SPRINT)

    data class RopeJump(
        val jumps: String = "",
        val time: String = ""
    ) : NewRecordState(type = NewRecordType.ROPE_JUMP)

    data class Custom(
        val performance: String = "",
        val time: String = ""
    ) : NewRecordState(type = NewRecordType.CUSTOM)

}

fun NewRecordState.toDomain(): PerformanceRecord {
    return when (this) {
        is NewRecordState.Weightlifting -> PerformanceRecord.Weightlifting(
            weight = weight.toInt(),
            sets = sets,
            repsPerSet = repsPerSet.mapValues {
                it.value.toInt()
            }
        )
        is NewRecordState.Sprint -> PerformanceRecord.Sprint(
            distance = distanceMeters.toInt(),
            time = time.toFloat()
        )
        is NewRecordState.RopeJump -> PerformanceRecord.RopeJump(
            jumps = jumps.toInt(),
            time = time.toFloat()
        )
        is NewRecordState.Custom -> PerformanceRecord.Custom(
            performance = performance,
            time = time.toFloat()
        )
        else -> throw IllegalStateException("$this can't be mapped")
    }
}