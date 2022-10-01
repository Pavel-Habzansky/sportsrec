package com.pavelhabzansky.data.features.sports_records.api

import com.pavelhabzansky.data.features.sports_records.common.PerformanceType
import com.squareup.moshi.Json

sealed class PerformanceRecordDto(@Json(name = "type") val type: PerformanceType) {

    data class Weightlifting(
        val weight: Int,
        val sets: Int,
        val repsPerSet: Map<Int, Int>
    ) : PerformanceRecordDto(PerformanceType.WEIGHTLIFTING)

    data class Sprint(
        val distance: Int,
        val time: Float
    ) : PerformanceRecordDto(PerformanceType.SPRINT)

    data class RopeJump(
        val jumps: Int,
        val time: Float
    ) : PerformanceRecordDto(PerformanceType.ROPE_JUMP)

    data class CustomRecord(
        val performance: String,
        val time: Float
    ) : PerformanceRecordDto(PerformanceType.CUSTOM)

}
