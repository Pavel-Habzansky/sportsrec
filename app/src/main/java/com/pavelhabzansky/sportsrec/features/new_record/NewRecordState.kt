package com.pavelhabzansky.sportsrec.features.new_record

import com.pavelhabzansky.domain.features.sports_records.model.PerformanceRecord
import com.pavelhabzansky.sportsrec.features.new_record.model.RecordType

sealed class NewRecord(
    val type: RecordType
) {
    object None : NewRecord(type = RecordType.NONE)

    data class Weightlifting(
        val weight: String = "",
        val sets: Int,
        val repsPerSet: Map<Int, String>
    ) : NewRecord(type = RecordType.WEIGHTLIFTING)

    data class Sprint(
        val distanceMeters: String = "",
        val time: String = ""
    ) : NewRecord(type = RecordType.SPRINT)

    data class RopeJump(
        val jumps: String = "",
        val time: String = ""
    ) : NewRecord(type = RecordType.ROPE_JUMP)

    data class Custom(
        val performance: String = "",
        val time: String = ""
    ) : NewRecord(type = RecordType.CUSTOM)

}

fun NewRecord.toDomain(): PerformanceRecord {
    return when (this) {
        is NewRecord.Weightlifting -> PerformanceRecord.Weightlifting(
            weight = weight.toInt(),
            sets = sets,
            repsPerSet = repsPerSet.mapValues {
                it.value.toInt()
            }
        )
        is NewRecord.Sprint -> PerformanceRecord.Sprint(
            distance = distanceMeters.toInt(),
            time = time.toFloat()
        )
        is NewRecord.RopeJump -> PerformanceRecord.RopeJump(
            jumps = jumps.toInt(),
            time = time.toFloat()
        )
        is NewRecord.Custom -> PerformanceRecord.Custom(
            performance = performance,
            time = time.toFloat()
        )
        else -> throw IllegalStateException("$this can't be mapped")
    }
}