package com.pavelhabzansky.data.features.sports_records.entity

import com.pavelhabzansky.data.features.sports_records.common.PerformanceType
import com.squareup.moshi.Json

sealed class PerformanceEntity(@Json(name = "type") val type: PerformanceType) {
    data class WeightliftingEntity(
        val weight: Int,
        val sets: Int,
        val repsPerSet: Map<Int, Int>
    ) : PerformanceEntity(PerformanceType.WEIGHTLIFTING)

    data class SprintEntity(
        val distance: Int,
        val time: Float
    ) : PerformanceEntity(PerformanceType.SPRINT)

    data class RopeJumpEntity(
        val jumps: Int,
        val time: Float
    ) : PerformanceEntity(PerformanceType.ROPE_JUMP)

    data class CustomRecordEntity(
        val performance: String,
        val time: Float
    ) : PerformanceEntity(PerformanceType.CUSTOM)
}